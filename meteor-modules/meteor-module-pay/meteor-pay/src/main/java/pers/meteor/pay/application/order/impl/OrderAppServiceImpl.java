package pers.meteor.pay.application.order.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.pay.application.channel.PayChannelQueryService;
import pers.meteor.pay.application.channel.PayService;
import pers.meteor.pay.application.channel.PayServiceFactory;
import pers.meteor.pay.application.order.OrderAppService;
import pers.meteor.pay.application.order.PrePaymentHandlerFactory;
import pers.meteor.pay.application.order.assembler.OrderAssembler;
import pers.meteor.pay.application.order.event.publisher.PayOrderEventPublisher;
import pers.meteor.pay.application.risk.RiskControlAppService;
import pers.meteor.pay.cmd.PayOrderCreateCmd;
import pers.meteor.pay.cmd.PayOrderSubmitCmd;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayRecord;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.service.PayOrderService;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppSimpleRespVO;

import java.util.List;

/**
 * @author meteor
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderAppServiceImpl implements OrderAppService {
    private final static OrderAssembler ORDER_ASSEMBLER = OrderAssembler.INSTANCE;

    private final PayOrderService payOrderService;
    private final PrePaymentHandlerFactory prePaymentHandlerFactory;
    private final PayOrderEventPublisher payOrderEventPublisher;

    private final PayServiceFactory payServiceFactory;
    private final RiskControlAppService riskControlAppService;
    private final PayChannelQueryService payChannelQueryService;

    @Override
    public Long createOrder(PayOrderCreateCmd payOrderCreateCmd) {
        // 检查支付运用
        PayAppSimpleRespVO payApp = payChannelQueryService.validPayApp(payOrderCreateCmd.getAppId());
        log.info("支付运用：{}({})", payApp.getName(), payApp.getCode());

        // 下单风控
        riskControlAppService.doOrderRiskControl(payOrderCreateCmd);

        // 组装参数
        PayOrder payOrder = ORDER_ASSEMBLER.toPayOrder(payOrderCreateCmd, payApp);

        // 创建订单
        payOrder = payOrderService.createOrder(payOrder);

        // 发布订单事件
        payOrderEventPublisher.payOrderCreated(payOrder);

        return payOrder.getOrderId();
    }

    @Override
    public PayResponse submitOrder(PayOrderSubmitCmd payRequest) {
        // 获取支付通道
        PayService payClient = validateAndGetPayService(payRequest.getPayClientId());
        log.info("支付通道：{}", payClient.getChannelId());

        // 校验订单是否可提交
        PayOrder payOrder = validateOrderCanSubmit(payRequest);

        // 组装参数
        payOrder = ORDER_ASSEMBLER.toSubmitOrder(payOrder, payRequest);

        // 支付风控
        riskControlAppService.doPayRiskControl(payRequest);

        // 订单前置处理
        prePaymentHandlerFactory.handle(payOrder);

        // 提交订单
        payOrder = payOrderService.submitOrder(payOrder);

        // 通道下单
        PayResponse payResponse = payClient.unifiedOrder(payOrder);

        // 修改支付结果
        payOrderService.savePayResult(payResponse);

        // 发布订单事件
        payOrderEventPublisher.payOrderPaid(payOrder);

        return payResponse;
    }

    /**
     * 校验并获取支付通道
     *
     * @param payClientId 支付通道ID
     * @return /
     */
    private PayService validateAndGetPayService(Long payClientId) {
        PayService payClient = payServiceFactory.getPayClient(payClientId);
        if (payClient == null) {
            throw new ServiceException("无效支付通道");
        }
        return payClient;
    }

    /**
     * 校验订单是否可提交
     *
     * @param payOrderSubmitCmd 提交参数
     * @return /
     */
    private PayOrder validateOrderCanSubmit(PayOrderSubmitCmd payOrderSubmitCmd) {
        // 检查支付订单
        PayOrder payOrder = payOrderService.checkOrder(payOrderSubmitCmd.getOrderId());
        // 校验通道订单是否已提交
        List<PayRecord> payRecords = payOrderService.listPayRecords(payOrder.getOrderId());
        for (PayRecord payRecord : payRecords) {
            if (payRecord.isSuccess()) {
                throw new ServiceException("订单已支付");
            }
            PayService payService = payServiceFactory.getPayClient(payRecord.getChannelId());
            if (payService == null) {
                log.error("支付通道不存在");
                continue;
            }
            PayResponse payResponse = payService.getOrder(payRecord.getOrderNo());
            if (payResponse != null && PayStatusEnum.isSuccess(payResponse.getPayStatus())) {
                throw new ServiceException("订单已支付, 请等待支付结果");
            }
        }
        return payOrder;
    }
}

package pers.meteor.pay.application.order.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.application.channel.PayClient;
import pers.meteor.pay.application.channel.PayClientFactory;
import pers.meteor.pay.application.order.OrderAppService;
import pers.meteor.pay.application.order.PrePaymentHandlerFactory;
import pers.meteor.pay.application.order.RiskControlAppService;
import pers.meteor.pay.application.order.assembler.OrderAssembler;
import pers.meteor.pay.application.order.event.publisher.PayOrderEventPublisher;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.service.PayOrderService;
import pers.meteor.pay.dto.PayRequestDto;

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
    private final PayClientFactory payClientFactory;
    private final RiskControlAppService riskControlAppService;
    private final PayOrderEventPublisher payOrderEventPublisher;

    @Override
    public PayResponse createOrder(PayRequestDto payRequest) {
        // 获取支付通道
        PayClient payClient = checkAndGetPayClient(payRequest.getPayClientId());

        // 组装参数
        PayOrder payOrder = ORDER_ASSEMBLER.toPayOrder(payRequest);

        // 风控管理
        riskControlAppService.doRiskControl(payClient, payOrder);

        // 订单前置处理
        prePaymentHandlerFactory.handle(payOrder);

        // 创建订单
        payOrder = payOrderService.createOrder(payOrder);

        // 通道下单
        PayResponse payResponse = payClient.unifiedOrder(payOrder);

        // 修改订单状态
        payOrderService.updateOrderStatus(payOrder.getOrderNo(), payResponse.getPayStatus());

        // 发布订单事件
        payOrderEventPublisher.payOrderCreated(payOrder);
        return payResponse;
    }

    private PayClient checkAndGetPayClient(Long payClientId) {
        PayClient payClient = payClientFactory.getPayClient(payClientId);
        if (payClient == null) {
            throw new ServiceException("无效支付通道");
        }
        return payClient;
    }
}

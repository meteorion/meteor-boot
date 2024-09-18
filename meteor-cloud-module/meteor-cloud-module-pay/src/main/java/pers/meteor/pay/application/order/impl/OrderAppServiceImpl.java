package pers.meteor.pay.application.order.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.application.channel.PayClient;
import pers.meteor.pay.application.channel.PayClientFactory;
import pers.meteor.pay.application.order.OrderAppService;
import pers.meteor.pay.application.order.assembler.OrderAssembler;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.service.OrderService;
import pers.meteor.pay.dto.PayRequestDto;

/**
 * @author meteor
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderAppServiceImpl implements OrderAppService {
    private final static OrderAssembler ORDER_ASSEMBLER = OrderAssembler.INSTANCE;

    private final OrderService orderService;
    private final PayClientFactory payClientFactory;

    /**
     * 创建订单
     *
     * @return /
     */
    public PayResponse createOrder(PayRequestDto payRequest) {
        // 获取支付通道
        PayClient payClient = checkAndGetPayClient(payRequest.getPayClientId());
        // 创建支付订单
        PayOrder payOrder = ORDER_ASSEMBLER.toPayOrder(payRequest);
        payOrder = orderService.createOrder(payOrder);
        // 通道下单
        PayResponse payResponse = payClient.unifiedOrder(payOrder);
        // 修改订单状态
        orderService.updateOrderStatus(payOrder.getOrderNo(), payResponse.getPayStatus());
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

package pers.meteor.pay.domain.order.service.impl;

import org.springframework.stereotype.Service;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;
import pers.meteor.pay.domain.order.service.PayOrderService;

/**
 * 订单服务
 *
 * @author meteor
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Override
    public PayOrder createOrder(PayOrder payOrder) {
        return null;
    }

    @Override
    public void updateOrderStatus(String orderNo, PayStatusEnum payStatus) {

    }
}

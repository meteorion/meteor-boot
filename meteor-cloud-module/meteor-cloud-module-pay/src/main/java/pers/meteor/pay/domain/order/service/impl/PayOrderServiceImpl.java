package pers.meteor.pay.domain.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.valueobject.PayRecord;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.repository.PayOrderRepository;
import pers.meteor.pay.domain.order.service.PayOrderService;

import java.util.Collections;
import java.util.List;

/**
 * 订单服务
 *
 * @author meteor
 */
@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl implements PayOrderService {
    private final PayOrderRepository payOrderRepository;

    @Override
    public PayOrder createOrder(PayOrder payOrder) {
        // 校验订单
        PayOrder order = payOrderRepository.selectByAppIdAndMerchantOrderNo(payOrder.getAppId(), payOrder.getMerchantOrderNo());
        if (order != null) {
            return order;
        }
        // 初始化订单
        payOrder.initCreate();
        // 保存订单
        payOrderRepository.insertOrder(payOrder);

        return payOrder;
    }

    @Override
    public PayOrder submitOrder(PayOrder payOrder) {
        return null;
    }

    @Override
    public void savePayResult(PayResponse payResponse) {

    }

    @Override
    public PayOrder checkOrder(Long orderId) {
        PayOrder payOrder = payOrderRepository.selectById(orderId);
        if (payOrder == null) {
            throw new ServiceException("支付订单不存在");
        }
        if(payOrder.isPaid()) {
            throw new ServiceException("订单已支付");
        }
        if (payOrder.canPay()) {
            throw new ServiceException("订单状态异常");
        }
        if (payOrder.isExpired()) {
            throw new ServiceException("订单已失效");
        }

        return payOrder;
    }

    @Override
    public List<PayRecord> listPayRecords(Long orderId) {
        return Collections.emptyList();
    }
}

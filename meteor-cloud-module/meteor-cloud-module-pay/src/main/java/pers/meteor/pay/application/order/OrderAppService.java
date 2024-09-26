package pers.meteor.pay.application.order;

import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.dto.PayRequestDto;

/**
 * 订单服务
 *
 * @author meteor
 */
public interface OrderAppService {
    /**
     * 创建订单
     *
     * @return /
     */
    PayResponse createOrder(PayRequestDto payRequest);
}

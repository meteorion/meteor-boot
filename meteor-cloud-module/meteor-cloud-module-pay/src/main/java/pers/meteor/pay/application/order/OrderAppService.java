package pers.meteor.pay.application.order;

import pers.meteor.pay.cmd.PayOrderSubmitCmd;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

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
    PayResponse submitOrder(PayOrderSubmitCmd payRequest);
}

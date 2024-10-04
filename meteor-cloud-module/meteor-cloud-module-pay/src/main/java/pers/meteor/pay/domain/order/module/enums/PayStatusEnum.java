package pers.meteor.pay.domain.order.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 支付状态
 * @author meteor
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    WAITING(0, "未支付"),
    SUCCESS(10, "支付成功"),
    REFUND(20, "已退款"),
    CLOSED(30, "支付关闭"),
    ;

    private final Integer status;
    private final String name;

    public static PayStatusEnum of(Integer status) {
        if (status == null) {
            return null;
        }
        for (PayStatusEnum value : values()) {
            if (Objects.equals(value.getStatus(), status)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 判断是否支付成功
     *
     * @param status 状态
     * @return 是否支付成功
     */
    public static boolean isSuccess(PayStatusEnum status) {
        return Objects.equals(status, SUCCESS);
    }

    /**
     * 判断是否已退款
     *
     * @param status 状态
     * @return 是否支付成功
     */
    public static boolean isRefund(PayStatusEnum status) {
        return Objects.equals(status, REFUND);
    }

    /**
     * 判断是否支付关闭
     *
     * @param status 状态
     * @return 是否支付关闭
     */
    public static boolean isClosed(PayStatusEnum status) {
        return Objects.equals(status, CLOSED);
    }
}

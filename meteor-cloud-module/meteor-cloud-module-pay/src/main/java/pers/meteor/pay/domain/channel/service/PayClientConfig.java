package pers.meteor.pay.domain.channel.service;

import javax.validation.Validator;

/**
 * @author meteor
 */
public interface PayClientConfig {

    /**
     * 校验通道配置
     *
     * @param validator /
     */
    default void validate(Validator validator) {
        // empty
    };
}

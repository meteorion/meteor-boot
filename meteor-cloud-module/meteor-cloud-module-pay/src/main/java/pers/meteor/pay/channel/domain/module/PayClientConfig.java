package pers.meteor.pay.channel.domain.module;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import pers.meteor.pay.channel.domain.module.enums.PayChannelEnum;

import javax.validation.Validator;

/**
 * 支付通道配置
 *
 * @author meteor
 */
@Data
public class PayClientConfig {
    /**
     * 配置id
     */
    private Long configId;
    /**
     * 支付通道类型
     */
    private PayChannelEnum payChannel;
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 运用id
     */
    private String appId;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 通道秘钥
     */
    private String secretKey;
    /**
     * 签名key
     */
    private String signKey;

    /**
     * 扩展参数
     */
    private JSONObject extendArgs;

    /**
     * 校验通道配置
     *
     * @param validator /
     */
    public void validate(Validator validator) {
        // empty
    };
}

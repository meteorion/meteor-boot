package pers.meteor.pay.domain.channel.module;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;

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
    private Long channelConfigId;
    /**
     * 支付通道id
     */
    private Long payChannelId;
    /**
     * 支付通道类型
     */
    private PayChannelEnum channelType;
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 运用id
     */
    private String appId;

    /**
     * 扩展参数
     */
    private JSONObject metadata;

    /**
     * 校验通道配置
     *
     * @param validator /
     */
    public void validate(Validator validator) {
        // empty
    };

    public String getMetadata(String key) {
        return metadata != null ? metadata.getString(key) : null;
    }
}

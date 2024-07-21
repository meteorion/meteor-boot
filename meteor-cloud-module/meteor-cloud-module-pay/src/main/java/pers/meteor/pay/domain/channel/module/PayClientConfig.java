package pers.meteor.pay.domain.channel.module;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.enums.SignTypeEnum;

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
     * 代理编号
     */
    private String agentId;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 签名类型
     */
    private SignTypeEnum signType;
    /**
     * 签名key
     */
    private String signKey;

    /**
     * 扩展参数
     */
    private JSONObject metedata;

    /**
     * 校验通道配置
     *
     * @param validator /
     */
    public void validate(Validator validator) {
        // empty
    };
}

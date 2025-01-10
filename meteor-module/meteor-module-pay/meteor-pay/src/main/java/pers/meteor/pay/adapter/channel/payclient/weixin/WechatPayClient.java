package pers.meteor.pay.adapter.channel.payclient.weixin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.common.core.utils.validation.ValidationUtils;
import pers.meteor.pay.domain.channel.module.PayClient;

import javax.validation.Validator;
import javax.validation.constraints.NotBlank;

/**
 * 支付通道配置
 *
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatPayClient extends PayClient {
    /**
     * API 版本 - V2
     * <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_1">V2 协议说明</a>
     */
    public static final String API_VERSION_V2 = "v2";
    /**
     * API 版本 - V3
     * <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay-1.shtml">V3 协议说明</a>
     */
    public static final String API_VERSION_V3 = "v3";

    /**
     * 商户号
     */
    @NotBlank(message = "商户号不能为空", groups = {V2.class, V3.class})
    private String mchId;

    /**
     * API 版本
     */
    @NotBlank(message = "API 版本不能为空", groups = {V2.class, V3.class})
    private String apiVersion;

    // ========== V2 版本的参数 ==========

    /**
     * 商户密钥
     */
    @NotBlank(message = "商户密钥不能为空", groups = V2.class)
    private String mchKey;
    /**
     * apiClient_cert.p12 证书文件的对应字符串【base64 格式】
     * 为什么采用 base64 格式？因为 p12 读取后是二进制，需要转换成 base64 格式才好传输和存储
     */
    @NotBlank(message = "apiClient_cert.p12 不能为空", groups = V2.class)
    private String keyContent;

    // ========== V3 版本的参数 ==========
    /**
     * apiClient_key.pem 证书文件的对应字符串
     */
    @NotBlank(message = "apiClient_key 不能为空", groups = V3.class)
    private String privateKeyContent;
    /**
     * apiClient_cert.pem 证书文件的对应的字符串
     */
    @NotBlank(message = "apiClient_cert 不能为空", groups = V3.class)
    private String privateCertContent;
    /**
     * apiV3 密钥值
     */
    @NotBlank(message = "apiV3 密钥值不能为空", groups = V3.class)
    private String apiV3Key;

    /**
     * 分组校验 v2版本
     */
    public interface V2 {
    }

    /**
     * 分组校验 v3版本
     */
    public interface V3 {
    }

    @Override
    public void validate(Validator validator) {
        ValidationUtils.validate(validator, this,
                API_VERSION_V2.equals(this.getApiVersion()) ? V2.class : V3.class);
    }
}

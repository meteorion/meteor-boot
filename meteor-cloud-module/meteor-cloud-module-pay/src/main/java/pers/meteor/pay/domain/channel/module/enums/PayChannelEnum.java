package pers.meteor.pay.domain.channel.module.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.valueobject.NonePayClientConfig;

/**
 * 支付通道类型
 *
 * @author meteor
 */
@Getter
@RequiredArgsConstructor
public enum PayChannelEnum {
    MOCK("mock", "模拟支付", NonePayClientConfig.class),

    WALLET("wallet", "钱包支付", NonePayClientConfig.class);

    /**
     * 编码
     * 参考 <a href="https://www.pingxx.com/api/支付渠道属性值.html">支付渠道属性值</a>
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    /**
     * 配置类
     */
    private final Class<? extends PayClientConfig> configClass;

    /**
     * 微信支付
     */
    public static final String WECHAT = "WECHAT";

    /**
     * 支付宝支付
     */
    public static final String ALIPAY = "ALIPAY";

    public static PayChannelEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }
}

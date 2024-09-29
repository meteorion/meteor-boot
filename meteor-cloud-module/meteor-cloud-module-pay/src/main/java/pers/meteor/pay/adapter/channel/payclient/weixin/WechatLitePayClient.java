package pers.meteor.pay.adapter.channel.payclient.weixin;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付【小程序】的 PayClient 实现类
 * 由于公众号和小程序的微信支付逻辑一致，所以直接进行继承
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml">JSAPI 下单</a>
 * @author zwy
 */
@Slf4j
public class WechatLitePayClient extends WechatPubPayClient {

    public WechatLitePayClient(Long channelId, WechatPayClient config) {
        super(channelId, config);
    }

}

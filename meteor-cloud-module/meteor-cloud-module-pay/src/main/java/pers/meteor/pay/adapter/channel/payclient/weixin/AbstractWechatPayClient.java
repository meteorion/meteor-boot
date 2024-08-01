package pers.meteor.pay.adapter.channel.payclient.weixin;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import pers.meteor.pay.application.channel.impl.AbstractPayClient;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.dto.PayRequest;
import pers.meteor.pay.dto.PayResponse;

/**
 * @author meteor
 */
public abstract class AbstractWechatPayClient extends AbstractPayClient {
    protected WxPayService wxPayService;

    public AbstractWechatPayClient(Long channelId, PayClientConfig config) {
        super(channelId, config);
    }

    @Override
    protected void doInit() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(config.getAppId());
        wxPayConfig.setMchId(config.getMetedata("mchId"));
        wxPayConfig.setMchKey(config.getMetedata("mchKey"));
        // weixin-pay-java 无法设置内容，只允许读取文件，所以这里要创建临时文件来解决

        wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
    }

}

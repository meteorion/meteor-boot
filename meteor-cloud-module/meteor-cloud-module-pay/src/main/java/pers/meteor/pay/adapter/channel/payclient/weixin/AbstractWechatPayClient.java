package pers.meteor.pay.adapter.channel.payclient.weixin;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import pers.meteor.pay.application.channel.impl.AbstractPayClient;
import pers.meteor.pay.dto.PayRequest;
import pers.meteor.pay.dto.PayResponse;

/**
 * @author meteor
 */
public abstract class AbstractWechatPayClient extends AbstractPayClient<WeiXinPayClientConfig> {
    protected WxPayService wxPayService;

    public AbstractWechatPayClient(Long channelId, WeiXinPayClientConfig config) {
        super(channelId, config);
    }

    @Override
    protected void doInit() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(config.getAppId());
        wxPayConfig.setMchId(config.getMetadata("mchId"));
        wxPayConfig.setMchKey(config.getMetadata("mchKey"));
        // weixin-pay-java 无法设置内容，只允许读取文件，所以这里要创建临时文件来解决

        wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
    }

    @Override
    protected PayResponse doUnifiedOrder(PayRequest payRequest) throws Exception {
        try {
            String apiVersion = config.getApiVersion();
            switch (apiVersion) {
                case WeiXinPayClientConfig.API_VERSION_V2:
                    return doUnifiedOrderV2(payRequest);
                case WeiXinPayClientConfig.API_VERSION_V3:
                    return doUnifiedOrderV3(payRequest);
                default:
                    throw new IllegalArgumentException("不支持的API版本:" + apiVersion);
            }
        }catch (WxPayException e) {

            return null;
        }
    }

    /**
     * V2下单
     *
     * @param payRequest 下单参数
     * @return  下单结果
     * @throws Exception /
     */
    protected abstract PayResponse doUnifiedOrderV2(PayRequest payRequest) throws Exception;

    /**
     * V3下单
     *
     * @param payRequest 下单参数
     * @return  下单结果
     * @throws Exception /
     */
    protected abstract PayResponse doUnifiedOrderV3(PayRequest payRequest) throws Exception;
}

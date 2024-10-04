package pers.meteor.pay.adapter.channel.payclient.weixin;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

/**
 * 微信支付【Native 二维码】的 PayClient 实现类
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_4_1.shtml">Native 下单</a>
 * @author meteor
 */
public class WechatNativePayService extends AbstractWechatPayService {

    public WechatNativePayService(Long channelId, WechatPayClient config) {
        super(channelId, config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.NATIVE);
    }

    @Override
    protected PayResponse doUnifiedOrderV2(PayOrder payOrder) throws Exception {
        // 构建支付请求
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(payOrder);
        // 发起支付
        WxPayUnifiedOrderResult response = wxPayService.unifiedOrder(request);

        return PayResponse.waitingOf(DisplayModeEnum.QR_CODE, response.getCodeURL(), payOrder.getOrderNo(), response);
    }

    @Override
    protected PayResponse doUnifiedOrderV3(PayOrder payOrder) throws Exception {
        // 构建支付请求
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(payOrder);
        // 发起支付
        String response =wxPayService.createOrderV3(TradeTypeEnum.NATIVE, request);

        return PayResponse.waitingOf(DisplayModeEnum.QR_CODE, response, payOrder.getOrderNo(), response);

    }
}

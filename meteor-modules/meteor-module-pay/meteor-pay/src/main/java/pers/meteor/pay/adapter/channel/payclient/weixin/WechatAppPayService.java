package pers.meteor.pay.adapter.channel.payclient.weixin;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.utils.json.JsonUtils;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;


/**
 * 微信支付【App 支付】的 PayClient 实现类
 *
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/open/pay/chapter2_5_3.shtml">App 支付</a>
 *
 * @author meteor
 */
@Slf4j
public class WechatAppPayService extends AbstractWechatPayService {

    public WechatAppPayService(Long channelId, WechatPayClient config) {
        super(channelId, config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.APP);
    }

    @Override
    protected PayResponse doUnifiedOrderV2(PayOrder payOrder) throws WxPayException {
        // 构建 WxPayUnifiedOrderRequest 对象
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(payOrder);
        // 执行请求
        WxPayMpOrderResult response = wxPayService.createOrder(request);
        // 转换结果
        return PayResponse.waitingOf(DisplayModeEnum.APP, JsonUtils.toJsonString(response), payOrder.getOrderNo(), response);
    }

    @Override
    protected PayResponse doUnifiedOrderV3(PayOrder payOrder) throws WxPayException {
        // 构建 WxPayUnifiedOrderV3Request 对象
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(payOrder);
        // 执行请求
        WxPayUnifiedOrderV3Result.AppResult response = wxPayService.createOrderV3(TradeTypeEnum.APP, request);
        // 转换结果
        return PayResponse.waitingOf(DisplayModeEnum.APP, JsonUtils.toJsonString(response), payOrder.getOrderNo(), response);
    }

}

package pers.meteor.pay.adapter.channel.payclient.weixin;

import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.utils.json.JsonUtils;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

/**
 * 微信支付（公众号）的 PayClient 实现类
 *
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_1.shtml">JSAPI 下单</a>
 *
 * @author meteor
 */
@Slf4j
public class WechatPubPayService extends AbstractWechatPayService {

    public WechatPubPayService(Long channelId, WechatPayClient config) {
        super(channelId, config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.JSAPI);
    }

    @Override
    protected PayResponse doUnifiedOrderV2(PayOrder payOrder) throws WxPayException {
        // 构建 WxPayUnifiedOrderRequest 对象
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(payOrder).setOpenid(getOpenid(payOrder));
        // 执行请求
        WxPayMpOrderResult response = wxPayService.createOrder(request);

        // 转换结果
        return PayResponse.waitingOf(DisplayModeEnum.APP, JsonUtils.toJsonString(response), payOrder.getOrderNo(), response);
    }

    @Override
    protected PayResponse doUnifiedOrderV3(PayOrder payOrder) throws WxPayException {
        // 构建 WxPayUnifiedOrderRequest 对象
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(payOrder)
                .setPayer(new WxPayUnifiedOrderV3Request.Payer().setOpenid(getOpenid(payOrder)));
        // 执行请求
        WxPayUnifiedOrderV3Result.JsapiResult response = wxPayService.createOrderV3(TradeTypeEnum.JSAPI, request);

        // 转换结果
        return PayResponse.waitingOf(DisplayModeEnum.APP, JsonUtils.toJsonString(response), payOrder.getOrderNo(), response);
    }

    // ========== 各种工具方法 ==========

    static String getOpenid(PayOrder payOrder) {
        String openid = payOrder.getPayer().getOpenId();
        if (StrUtil.isEmpty(openid)) {
            throw new ServiceException("支付请求的 openid 不能为空！");
        }
        return openid;
    }

}

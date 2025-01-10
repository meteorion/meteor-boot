package pers.meteor.pay.adapter.channel.payclient.weixin;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.utils.json.JsonUtils;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.Goods;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


/**
 * 微信支付【付款码支付】的 PayClient 实现类
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1">付款码支付</a>
 *
 * @author meteor
 */
@Slf4j
public class WechatBarPayService extends AbstractWechatPayService {

    /**
     * 微信付款码的过期时间
     */
    private static final Duration AUTH_CODE_EXPIRE = Duration.ofMinutes(3);

    public WechatBarPayService(Long channelId, WechatPayClient config) {
        super(channelId, config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.MICROPAY);
    }

    @Override
    protected PayResponse doUnifiedOrderV2(PayOrder payOrder) throws WxPayException {
        // 由于付款码需要不断轮询，所以需要在较短的时间完成支付
        LocalDateTime expireTime = LocalDateTime.now().plus(AUTH_CODE_EXPIRE);
        if (expireTime.isAfter(payOrder.getExpireTime())) {
            expireTime = payOrder.getExpireTime();
        }
        // 构建 WxPayMicropayRequest 对象
        Goods goods = payOrder.getGoods();
        WxPayMicropayRequest request = WxPayMicropayRequest.newBuilder()
                .outTradeNo(payOrder.getOrderNo())
                .body(goods.getDescription())
                .detail(JsonUtils.toJsonString(goods))
                .totalFee(payOrder.getAmount()) // 单位分
                .timeExpire(formatDateV2(expireTime))
                .spbillCreateIp(payOrder.getPayer().getClientIp())
                .authCode(getAuthCode(payOrder))
                .build();
        // 执行请求，重试直到失败（过期），或者成功
        WxPayException lastWxPayException = null;
        for (int i = 1; i < Byte.MAX_VALUE; i++) {
            try {
                WxPayMicropayResult response = wxPayService.micropay(request);
                // 支付成功，例如说：1）用户输入了密码；2）用户免密支付
                PayResponse payResponse = PayResponse.successOf(response.getTransactionId(), response.getOpenid(),
                        parseDateV2(response.getTimeEnd()), response.getOutTradeNo(), response);
                payResponse.setDisplayMode(DisplayModeEnum.BAR_CODE);
                return payResponse;
            } catch (WxPayException ex) {
                lastWxPayException = ex;
                // 如果不满足这 3 种任一的，则直接抛出 WxPayException 异常，不仅需处理
                // 1. SYSTEMERROR：接口返回错误：请立即调用被扫订单结果查询API，查询当前订单状态，并根据订单的状态决定下一步的操作。
                // 2. USERPAYING：用户支付中，需要输入密码：等待 5 秒，然后调用被扫订单结果查询 API，查询当前订单的不同状态，决定下一步的操作。
                // 3. BANKERROR：银行系统异常：请立即调用被扫订单结果查询 API，查询当前订单的不同状态，决定下一步的操作。
                if (!StrUtil.equalsAny(ex.getErrCode(), "SYSTEMERROR", "USERPAYING", "BANKERROR")) {
                    throw ex;
                }
                // 等待 5 秒，继续下一轮重新发起支付
                log.info("[doUnifiedOrderV2][发起微信 Bar 支付第({})失败，等待下一轮重试，请求({})，响应({})]", i, JsonUtils.toJsonString(request), ex.getMessage());
                ThreadUtil.sleep(5, TimeUnit.SECONDS);
            }
        }
        throw lastWxPayException;
    }

    @Override
    protected PayResponse doUnifiedOrderV3(PayOrder payOrder) throws WxPayException {
        return doUnifiedOrderV2(payOrder);
    }

    static String getAuthCode(PayOrder payOrder) {
        String authCode = payOrder.getMetadata("authCode");
        if (StrUtil.isEmpty(authCode)) {
            throw new ServiceException("支付请求的 authCode 不能为空！");
        }
        return authCode;
    }
}

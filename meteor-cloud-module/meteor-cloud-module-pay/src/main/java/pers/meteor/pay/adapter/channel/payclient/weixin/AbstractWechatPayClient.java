package pers.meteor.pay.adapter.channel.payclient.weixin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.TemporalAccessorUtil;
import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.*;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import pers.meteor.common.core.utils.StringUtils;
import pers.meteor.common.core.utils.json.JsonUtils;
import pers.meteor.pay.application.channel.impl.AbstractPayClient;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.RefundOrder;
import pers.meteor.pay.domain.order.module.TransferOrder;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;
import pers.meteor.pay.domain.order.module.valueobject.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static cn.hutool.core.date.DatePattern.*;

/**
 * @author meteor
 */
public abstract class AbstractWechatPayClient extends AbstractPayClient<WechatPayClientConfig> {
    protected WxPayService wxPayService;

    public AbstractWechatPayClient(Long channelId, WechatPayClientConfig config) {
        super(channelId, config);
    }

    private static Integer parseStatus(String tradeState) {
        switch (tradeState) {
            case "NOTPAY":
            case "USERPAYING": // 支付中，等待用户输入密码（条码支付独有）
                return PayStatusEnum.WAITING.getStatus();
            case "SUCCESS":
                return PayStatusEnum.SUCCESS.getStatus();
            case "REFUND":
                return PayStatusEnum.REFUND.getStatus();
            case "CLOSED":
            case "REVOKED": // 已撤销（刷卡支付独有）
            case "PAYERROR": // 支付失败（其它原因，如银行返回失败）
                return PayStatusEnum.CLOSED.getStatus();
            default:
                throw new IllegalArgumentException(StrUtil.format("未知的支付状态({})", tradeState));
        }
    }

    static String formatDateV2(LocalDateTime time) {
        return TemporalAccessorUtil.format(time.atZone(ZoneId.systemDefault()), PURE_DATETIME_PATTERN);
    }

    static LocalDateTime parseDateV2(String time) {
        return LocalDateTimeUtil.parse(time, PURE_DATETIME_PATTERN);
    }

    static LocalDateTime parseDateV2B(String time) {
        return LocalDateTimeUtil.parse(time, NORM_DATETIME_PATTERN);
    }

    static String formatDateV3(LocalDateTime time) {
        return TemporalAccessorUtil.format(time.atZone(ZoneId.systemDefault()), UTC_WITH_XXX_OFFSET_PATTERN);
    }

    static LocalDateTime parseDateV3(String time) {
        return LocalDateTimeUtil.parse(time, UTC_WITH_XXX_OFFSET_PATTERN);
    }

    static String getErrorCode(WxPayException e) {
        if (StrUtil.isNotEmpty(e.getErrCode())) {
            return e.getErrCode();
        }
        if (StrUtil.isNotEmpty(e.getCustomErrorMsg())) {
            return "CUSTOM_ERROR";
        }
        return e.getReturnCode();
    }

    static String getErrorMessage(WxPayException e) {
        if (StrUtil.isNotEmpty(e.getErrCode())) {
            return e.getErrCodeDes();
        }
        if (StrUtil.isNotEmpty(e.getCustomErrorMsg())) {
            return e.getCustomErrorMsg();
        }
        return e.getReturnMsg();
    }

    protected void doInit(String tradeType) {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(config.getAppId());
        wxPayConfig.setTradeType(tradeType);
        wxPayConfig.setMchId(config.getMetadata("mchId"));
        wxPayConfig.setMchKey(config.getMetadata("mchKey"));
        // weixin-pay-java 无法设置内容，只允许读取文件，所以这里要创建临时文件来解决

        wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
    }

    @Override
    protected PayResponse doUnifiedOrder(PayOrder payOrder) throws Exception {
        try {
            String apiVersion = config.getApiVersion();
            switch (apiVersion) {
                case WechatPayClientConfig.API_VERSION_V2:
                    return doUnifiedOrderV2(payOrder);
                case WechatPayClientConfig.API_VERSION_V3:
                    return doUnifiedOrderV3(payOrder);
                default:
                    throw new IllegalArgumentException("不支持的API版本:" + apiVersion);
            }
        } catch (WxPayException e) {

            return null;
        }
    }

    /**
     * V2下单
     *
     * @param payOrder 下单参数
     * @return 下单结果
     * @throws Exception /
     */
    protected abstract PayResponse doUnifiedOrderV2(PayOrder payOrder) throws Exception;

    /**
     * 【V2】创建微信下单请求
     *
     * @param payOrder 下单信息
     * @return 下单请求
     */
    protected WxPayUnifiedOrderRequest buildPayUnifiedOrderRequestV2(PayOrder payOrder) {
        Goods goods = payOrder.getGoods();
        Payer payer = payOrder.getPayer();
        return WxPayUnifiedOrderRequest.newBuilder().outTradeNo(payOrder.getOrderNo()).body(goods.getDescription()).detail(JsonUtils.toJsonString(goods)).goodsTag(goods.getTag()).totalFee(payOrder.getAmount()) // 单位分
                .timeExpire(formatDateV2(payOrder.getExpireTime())).spbillCreateIp(payer.getClientIp()).notifyUrl(payOrder.getNotifyUrl()).build();
    }

    /**
     * V3下单
     *
     * @param payOrder 下单参数
     * @return 下单结果
     * @throws Exception /
     */
    protected abstract PayResponse doUnifiedOrderV3(PayOrder payOrder) throws Exception;

    /**
     * 【V3】创建微信下单请求
     *
     * @param payOrder 下单信息
     * @return 下单请求
     */
    protected WxPayUnifiedOrderV3Request buildPayUnifiedOrderRequestV3(PayOrder payOrder) {
        Goods goods = payOrder.getGoods();
        Payer payer = payOrder.getPayer();

        WxPayUnifiedOrderV3Request request = new WxPayUnifiedOrderV3Request();
        request.setOutTradeNo(payOrder.getOrderNo());
        request.setDescription(goods.getDescription());
        request.setAmount(new WxPayUnifiedOrderV3Request.Amount().setTotal(payOrder.getAmount())); // 单位分
        request.setTimeExpire(formatDateV3(payOrder.getExpireTime()));
        request.setSceneInfo(new WxPayUnifiedOrderV3Request.SceneInfo().setPayerClientIp(payer.getClientIp()));
        request.setNotifyUrl(payOrder.getNotifyUrl());
        return request;
    }

    @Override
    protected PayResponse doParseOrderNotify(String body) throws Throwable {
        switch (config.getApiVersion()) {
            case WechatPayClientConfig.API_VERSION_V2:
                return doParseOrderNotifyV2(body);
            case WechatPayClientConfig.API_VERSION_V3:
                return doParseOrderNotifyV3(body);
            default:
                throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
        }
    }

    private PayResponse doParseOrderNotifyV2(String body) throws WxPayException {
        // 1. 解析回调
        WxPayOrderNotifyResult response = wxPayService.parseOrderNotifyResult(body);
        // 2. 构建结果
        // V2 微信支付的回调，只有 SUCCESS 支付成功、CLOSED 支付失败两种情况，无需像支付宝一样解析的比较复杂
        Integer status = Objects.equals(response.getResultCode(), "SUCCESS") ? PayStatusEnum.SUCCESS.getStatus() : PayStatusEnum.CLOSED.getStatus();
        return PayResponse.of(status, response.getTransactionId(), response.getOpenid(), parseDateV2(response.getTimeEnd()), response.getOutTradeNo(), body);
    }

    private PayResponse doParseOrderNotifyV3(String body) throws WxPayException {
        // 1. 解析回调
        WxPayNotifyV3Result response = wxPayService.parseOrderNotifyV3Result(body, null);
        WxPayNotifyV3Result.DecryptNotifyResult result = response.getResult();
        // 2. 构建结果
        Integer status = parseStatus(result.getTradeState());
        String openid = result.getPayer() != null ? result.getPayer().getOpenid() : null;
        return PayResponse.of(status, result.getTransactionId(), openid, parseDateV3(result.getSuccessTime()), result.getOutTradeNo(), body);
    }

    @Override
    protected PayResponse doGetOrder(String outTradeNo) throws Throwable {
        try {
            switch (config.getApiVersion()) {
                case WechatPayClientConfig.API_VERSION_V2:
                    return doGetOrderV2(outTradeNo);
                case WechatPayClientConfig.API_VERSION_V3:
                    return doGetOrderV3(outTradeNo);
                default:
                    throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
            }
        } catch (WxPayException e) {
            if (StringUtils.equalsAny(e.getErrCode(), "ORDERNOTEXIST", "ORDER_NOT_EXIST")) {
                String errorCode = getErrorCode(e);
                String errorMessage = getErrorMessage(e);
                return PayResponse.closedOf(errorCode, errorMessage, outTradeNo, e.getXmlString());
            }
            throw e;
        }
    }

    private PayResponse doGetOrderV2(String outTradeNo) throws WxPayException {
        // 构建 WxPayUnifiedOrderRequest 对象
        WxPayOrderQueryRequest request = WxPayOrderQueryRequest.newBuilder().outTradeNo(outTradeNo).build();
        // 执行请求
        WxPayOrderQueryResult response = wxPayService.queryOrder(request);

        // 转换结果
        Integer status = parseStatus(response.getTradeState());
        return PayResponse.of(status, response.getTransactionId(), response.getOpenid(), parseDateV2(response.getTimeEnd()), outTradeNo, response);
    }

    private PayResponse doGetOrderV3(String outTradeNo) throws WxPayException {
        // 构建 WxPayUnifiedOrderRequest 对象
        WxPayOrderQueryV3Request request = new WxPayOrderQueryV3Request().setOutTradeNo(outTradeNo);
        // 执行请求
        WxPayOrderQueryV3Result response = wxPayService.queryOrderV3(request);

        // 转换结果
        Integer status = parseStatus(response.getTradeState());
        String openid = response.getPayer() != null ? response.getPayer().getOpenid() : null;
        return PayResponse.of(status, response.getTransactionId(), openid, parseDateV3(response.getSuccessTime()), outTradeNo, response);
    }

    @Override
    protected RefundResponse doUnifiedRefund(RefundOrder refundOrder) throws Throwable {
        try {
            switch (config.getApiVersion()) {
                case WechatPayClientConfig.API_VERSION_V2:
                    return doUnifiedRefundV2(refundOrder);
                case WechatPayClientConfig.API_VERSION_V3:
                    return doUnifiedRefundV3(refundOrder);
                default:
                    throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
            }
        } catch (WxPayException e) {
            String errorCode = getErrorCode(e);
            String errorMessage = getErrorMessage(e);
            return RefundResponse.failureOf(errorCode, errorMessage, refundOrder.getPayOrderNo(), e.getXmlString());
        }
    }

    private RefundResponse doUnifiedRefundV2(RefundOrder refundOrder) throws Throwable {
        // 1. 构建 WxPayRefundRequest 请求
        WxPayRefundRequest request = new WxPayRefundRequest().setOutTradeNo(refundOrder.getPayOrderNo()).setOutRefundNo(refundOrder.getRufundOrderNo()).setRefundFee(refundOrder.getRefundPrice()).setRefundDesc(refundOrder.getReason()).setTotalFee(refundOrder.getPayPrice()).setNotifyUrl(refundOrder.getNotifyUrl());
        // 2.1 执行请求
        WxPayRefundResult response = wxPayService.refundV2(request);
        // 2.2 创建返回结果
        if (Objects.equals("SUCCESS", response.getResultCode())) { // V2 情况下，不直接返回退款成功，而是等待异步通知
            return RefundResponse.waitingOf(response.getRefundId(), refundOrder.getRufundOrderNo(), response);
        }
        return RefundResponse.failureOf(refundOrder.getRufundOrderNo(), response);
    }

    private RefundResponse doUnifiedRefundV3(RefundOrder refundOrder) throws Throwable {
        // 1. 构建 WxPayRefundRequest 请求
        WxPayRefundV3Request request = new WxPayRefundV3Request().setOutTradeNo(refundOrder.getPayOrderNo()).setOutRefundNo(refundOrder.getRufundOrderNo()).setAmount(new WxPayRefundV3Request.Amount().setRefund(refundOrder.getRefundPrice()).setTotal(refundOrder.getPayPrice()).setCurrency("CNY")).setReason(refundOrder.getReason()).setNotifyUrl(refundOrder.getNotifyUrl());
        // 2.1 执行请求
        WxPayRefundV3Result response = wxPayService.refundV3(request);
        // 2.2 创建返回结果
        if (Objects.equals("SUCCESS", response.getStatus())) {
            return RefundResponse.successOf(response.getRefundId(), parseDateV3(response.getSuccessTime()), refundOrder.getRufundOrderNo(), response);
        }
        if (Objects.equals("PROCESSING", response.getStatus())) {
            return RefundResponse.waitingOf(response.getRefundId(), refundOrder.getRufundOrderNo(), response);
        }
        return RefundResponse.failureOf(refundOrder.getRufundOrderNo(), response);
    }

    // ========== 各种工具方法 ==========

    @Override
    protected RefundResponse doParseRefundNotify(String body) throws Throwable {
        switch (config.getApiVersion()) {
            case WechatPayClientConfig.API_VERSION_V2:
                return doParseRefundNotifyV2(body);
            case WechatPayClientConfig.API_VERSION_V3:
                return parseRefundNotifyV3(body);
            default:
                throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
        }
    }

    private RefundResponse doParseRefundNotifyV2(String body) throws WxPayException {
        // 1. 解析回调
        WxPayRefundNotifyResult response = wxPayService.parseRefundNotifyResult(body);
        WxPayRefundNotifyResult.ReqInfo result = response.getReqInfo();
        // 2. 构建结果
        if (Objects.equals("SUCCESS", result.getRefundStatus())) {
            return RefundResponse.successOf(result.getRefundId(), parseDateV2B(result.getSuccessTime()), result.getOutRefundNo(), response);
        }
        return RefundResponse.failureOf(result.getOutRefundNo(), response);
    }

    private RefundResponse parseRefundNotifyV3(String body) throws WxPayException {
        // 1. 解析回调
        WxPayRefundNotifyV3Result response = wxPayService.parseRefundNotifyV3Result(body, null);
        WxPayRefundNotifyV3Result.DecryptNotifyResult result = response.getResult();
        // 2. 构建结果
        if (Objects.equals("SUCCESS", result.getRefundStatus())) {
            return RefundResponse.successOf(result.getRefundId(), parseDateV3(result.getSuccessTime()), result.getOutRefundNo(), response);
        }
        return RefundResponse.failureOf(result.getOutRefundNo(), response);
    }

    @Override
    protected RefundResponse doGetRefund(String payOrderNo, String refundOrderNo) throws Throwable {
        try {
            switch (config.getApiVersion()) {
                case WechatPayClientConfig.API_VERSION_V2:
                    return doGetRefundV2(payOrderNo, refundOrderNo);
                case WechatPayClientConfig.API_VERSION_V3:
                    return doGetRefundV3(payOrderNo, refundOrderNo);
                default:
                    throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
            }
        } catch (WxPayException e) {
            if (StringUtils.equalsAny(e.getErrCode(), "REFUNDNOTEXIST", "RESOURCE_NOT_EXISTS")) {
                String errorCode = getErrorCode(e);
                String errorMessage = getErrorMessage(e);
                return RefundResponse.failureOf(errorCode, errorMessage, refundOrderNo, e.getXmlString());
            }
            throw e;
        }
    }

    private RefundResponse doGetRefundV2(String outTradeNo, String outRefundNo) throws WxPayException {
        // 1. 构建 WxPayRefundRequest 请求
        WxPayRefundQueryRequest request = WxPayRefundQueryRequest.newBuilder().outTradeNo(outTradeNo).outRefundNo(outRefundNo).build();
        // 2.1 执行请求
        WxPayRefundQueryResult response = wxPayService.refundQuery(request);
        // 2.2 创建返回结果
        if (!Objects.equals("SUCCESS", response.getResultCode())) {
            return RefundResponse.waitingOf(null, outRefundNo, response);
        }
        WxPayRefundQueryResult.RefundRecord refund = CollUtil.findOne(response.getRefundRecords(), record -> record.getOutRefundNo().equals(outRefundNo));
        if (refund == null) {
            return RefundResponse.failureOf(outRefundNo, response);
        }
        switch (refund.getRefundStatus()) {
            case "SUCCESS":
                return RefundResponse.successOf(refund.getRefundId(), parseDateV2B(refund.getRefundSuccessTime()), outRefundNo, response);
            case "PROCESSING":
                return RefundResponse.waitingOf(refund.getRefundId(), outRefundNo, response);
            case "CHANGE": // 退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款
            case "FAIL":
                return RefundResponse.failureOf(outRefundNo, response);
            default:
                throw new IllegalArgumentException(String.format("未知的退款状态(%s)", refund.getRefundStatus()));
        }
    }

    private RefundResponse doGetRefundV3(String outTradeNo, String outRefundNo) throws WxPayException {
        // 1. 构建 WxPayRefundRequest 请求
        WxPayRefundQueryV3Request request = new WxPayRefundQueryV3Request();
        request.setOutRefundNo(outRefundNo);
        // 2.1 执行请求
        WxPayRefundQueryV3Result response = wxPayService.refundQueryV3(request);
        // 2.2 创建返回结果
        switch (response.getStatus()) {
            case "SUCCESS":
                return RefundResponse.successOf(response.getRefundId(), parseDateV3(response.getSuccessTime()), outRefundNo, response);
            case "PROCESSING":
                return RefundResponse.waitingOf(response.getRefundId(), outRefundNo, response);
            case "ABNORMAL": // 退款异常
            case "CLOSED":
                return RefundResponse.failureOf(outRefundNo, response);
            default:
                throw new IllegalArgumentException(String.format("未知的退款状态(%s)", response.getStatus()));
        }
    }

    @Override
    protected TransferResponse doUnifiedTransfer(TransferOrder transferOrder) throws Throwable {
        return null;
    }
}

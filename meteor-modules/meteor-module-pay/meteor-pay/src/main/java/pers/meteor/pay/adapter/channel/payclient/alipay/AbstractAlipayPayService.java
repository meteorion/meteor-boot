package pers.meteor.pay.adapter.channel.payclient.alipay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.utils.StringUtils;
import pers.meteor.common.utils.json.JsonUtils;
import pers.meteor.pay.application.channel.impl.AbstractPayService;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.RefundOrder;
import pers.meteor.pay.domain.order.module.TransferOrder;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;
import pers.meteor.pay.domain.order.module.enums.TransferTypeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.module.valueobject.RefundResponse;
import pers.meteor.pay.domain.order.module.valueobject.TransferResponse;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;
import static pers.meteor.pay.adapter.channel.payclient.alipay.AlipayPayClient.MODE_CERTIFICATE;

/**
 * 支付宝抽象类，实现支付宝统一的接口、以及部分实现（退款）
 *
 * @author jason
 */
@Getter
@Slf4j
public abstract class AbstractAlipayPayService extends AbstractPayService<AlipayPayClient> {

    // 仅用于单测场景
    protected DefaultAlipayClient client;

    public AbstractAlipayPayService(Long channelId, AlipayPayClient config) {
        super(channelId, config);
    }

    private static Integer parseStatus(String tradeStatus) {
        return Objects.equals("WAIT_BUYER_PAY", tradeStatus) ? PayStatusEnum.WAITING.getStatus() : StringUtils.equalsAny(tradeStatus, "TRADE_FINISHED", "TRADE_SUCCESS") ? PayStatusEnum.SUCCESS.getStatus() : Objects.equals("TRADE_CLOSED", tradeStatus) ? PayStatusEnum.CLOSED.getStatus() : null;
    }

    // ============ 支付相关 ==========

    @Override
    @SneakyThrows
    protected void doInit() {
        AlipayConfig alipayConfig = new AlipayConfig();
        BeanUtil.copyProperties(config, alipayConfig, false);
        this.client = new DefaultAlipayClient(alipayConfig);
    }

    /**
     * 构造支付关闭的 {@link PayResponse} 对象
     *
     * @return 支付关闭的 {@link PayResponse} 对象
     */
    protected PayResponse buildClosedPayResponse(PayOrder payOrder, AlipayResponse response) {
        Assert.isFalse(response.isSuccess());
        return PayResponse.closedOf(response.getSubCode(), response.getSubMsg(), payOrder.getOrderNo(), response);
    }

    @Override
    public PayResponse doParseOrderNotify(String body) throws Throwable {
        // 1. 校验回调数据
        Map<String, String> bodyObj = HttpUtil.decodeParamMap(body, StandardCharsets.UTF_8);
        AlipaySignature.rsaCheckV1(bodyObj, config.getAlipayPublicKey(), StandardCharsets.UTF_8.name(), config.getSignType());

        // 2. 解析订单的状态
        // 额外说明：支付宝不仅仅支付成功会回调，再各种触发支付单数据变化时，都会进行回调，所以这里 status 的解析会写的比较复杂
        Integer status = parseStatus(bodyObj.get("trade_status"));
        // 特殊逻辑: 支付宝没有退款成功的状态，所以，如果有退款金额，我们认为是退款成功
        if (MapUtil.getDouble(bodyObj, "refund_fee", 0D) > 0) {
            status = PayStatusEnum.REFUND.getStatus();
        }
        Assert.notNull(status, (Supplier<Throwable>) () -> {
            throw new IllegalArgumentException(StrUtil.format("body({}) 的 trade_status 不正确", body));
        });
        Map<String, String> params = JsonUtils.parseObject(body, new TypeReference<Map<String, String>>() {
        });
        return PayResponse.of(status, bodyObj.get("trade_no"), bodyObj.get("seller_id"), parseTime(params.get("gmt_payment")), bodyObj.get("out_trade_no"), body);
    }

    @Override
    protected PayResponse doGetOrder(String outTradeNo) throws Throwable {
        // 1.1 构建 AlipayTradeRefundModel 请求
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        // 1.2 构建 AlipayTradeQueryRequest 请求
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        AlipayTradeQueryResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) {
            // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) { // 不成功，例如说订单不存在
            return PayResponse.closedOf(response.getSubCode(), response.getSubMsg(), outTradeNo, response);
        }
        // 2.2 解析订单的状态
        Integer status = parseStatus(response.getTradeStatus());
        Assert.notNull(status, () -> {
            throw new IllegalArgumentException(StrUtil.format("body({}) 的 trade_status 不正确", response.getBody()));
        });
        return PayResponse.of(status, response.getTradeNo(), response.getBuyerUserId(), LocalDateTimeUtil.of(response.getSendPayDate()), outTradeNo, response);
    }

    // ============ 退款相关 ==========

    /**
     * 支付宝统一的退款接口 alipay.trade.refund
     *
     * @param refundOrder 退款请求 request DTO
     * @return 退款请求 Response
     */
    @Override
    protected RefundResponse doUnifiedRefund(RefundOrder refundOrder) throws AlipayApiException {
        // 1.1 构建 AlipayTradeRefundModel 请求
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(refundOrder.getPayOrderNo());
        model.setOutRequestNo(refundOrder.getRefundOrderNo());
        model.setRefundAmount(formatAmount(refundOrder.getRefundPrice()));
        model.setRefundReason(refundOrder.getReason());
        // 1.2 构建 AlipayTradePayRequest 请求
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);

        // 2.1 执行请求
        AlipayTradeRefundResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) {  // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) {
            // 当出现 ACQ.SYSTEM_ERROR, 退款可能成功也可能失败。 返回 WAIT 状态. 后续 job 会轮询
            if (StringUtils.equalsAny(response.getSubCode(), "ACQ.SYSTEM_ERROR", "SYSTEM_ERROR")) {
                return RefundResponse.waitingOf(null, refundOrder.getRefundOrderNo(), response);
            }
            return RefundResponse.failureOf(response.getSubCode(), response.getSubMsg(), refundOrder.getRefundOrderNo(), response);
        }
        // 2.2 创建返回结果
        // 支付宝只要退款调用返回 success，就认为退款成功，不需要回调。具体可见 parseNotify 方法的说明。
        // 另外，支付宝没有退款单号，所以不用设置
        return RefundResponse.successOf(null, LocalDateTimeUtil.of(response.getGmtRefundPay()), refundOrder.getRefundOrderNo(), response);
    }

    @Override
    public RefundResponse doParseRefundNotify(String body) {
        // 补充说明：支付宝退款时，没有回调，这点和微信支付是不同的。并且，退款分成部分退款、和全部退款。
        // ① 部分退款：是会有回调，但是它回调的是订单状态的同步回调，不是退款订单的回调
        // ② 全部退款：Wap 支付有订单状态的同步回调，但是 PC/扫码又没有
        // 所以，这里在解析时，即使是退款导致的订单状态同步，我们也忽略不做为“退款同步”，而是订单的回调。
        // 实际上，支付宝退款只要发起成功，就可以认为退款成功，不需要等待回调。
        throw new UnsupportedOperationException("支付宝无退款回调");
    }

    @Override
    protected RefundResponse doGetRefundOrder(String outTradeNo, String outRefundNo) throws AlipayApiException {
        // 1.1 构建 AlipayTradeFastpayRefundQueryModel 请求
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(outTradeNo);
        model.setOutRequestNo(outRefundNo);
        model.setQueryOptions(Collections.singletonList("gmt_refund_pay"));
        // 1.2 构建 AlipayTradeFastpayRefundQueryRequest 请求
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(model);

        // 2.1 执行请求
        AlipayTradeFastpayRefundQueryResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) { // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) {
            // 明确不存在的情况，应该就是失败，可进行关闭
            if (StringUtils.equalsAny(response.getSubCode(), "TRADE_NOT_EXIST", "ACQ.TRADE_NOT_EXIST")) {
                return RefundResponse.failureOf(outRefundNo, response);
            }
            // 可能存在“ACQ.SYSTEM_ERROR”系统错误等情况，所以返回 WAIT 继续等待
            return RefundResponse.waitingOf(null, outRefundNo, response);
        }
        // 2.2 创建返回结果
        if (Objects.equals(response.getRefundStatus(), "REFUND_SUCCESS")) {
            return RefundResponse.successOf(null, LocalDateTimeUtil.of(response.getGmtRefundPay()), outRefundNo, response);
        }
        return RefundResponse.waitingOf(null, outRefundNo, response);
    }

    @Override
    protected TransferResponse doUnifiedTransfer(TransferOrder transferOrder) throws AlipayApiException {
        // 1.1 校验公钥类型 必须使用公钥证书模式
        if (!Objects.equals(config.getMode(), MODE_CERTIFICATE)) {
            throw new IllegalStateException("支付宝单笔转账必须使用公钥证书模式");
        }

        // 1.2 构建 AlipayFundTransUniTransferModel
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        // ① 通用的参数
        model.setTransAmount(formatAmount(transferOrder.getAmount())); // 转账金额
        model.setOrderTitle(transferOrder.getTitle());               // 转账业务的标题，用于在支付宝用户的账单里显示。
        model.setOutBizNo(transferOrder.getTransferOrderNo());
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");    // 销售产品码。单笔无密转账固定为 TRANS_ACCOUNT_NO_PWD
        model.setBizScene("DIRECT_TRANSFER");           // 业务场景 单笔无密转账固定为 DIRECT_TRANSFER
        model.setBusinessParams(JsonUtils.toJsonString(transferOrder.getMetadata()));
        TransferTypeEnum transferType = transferOrder.getTransferType();
        switch (transferType) {
            // TODO @jason：是不是不用传递 transferType 参数哈？因为应该已经明确是支付宝啦？
            // @芋艿。 是不是还要考虑转账到银行卡。所以传 transferType 但是转账到银行卡不知道要如何测试??
            case ALIPAY_BALANCE: {
                // ② 个性化的参数
                Participant payeeInfo = new Participant();
                payeeInfo.setIdentityType("ALIPAY_LOGON_ID");
                String logonId = transferOrder.getPayee().getOpenId();
                if (StrUtil.isEmpty(logonId)) {
                    throw new ServiceException("支付包登录 ID 不能为空");
                }
                String accountName = transferOrder.getPayee().getAccountName();
                if (StrUtil.isEmpty(accountName)) {
                    throw new ServiceException("支付包账户名称不能为空");
                }
                payeeInfo.setIdentity(logonId); // 支付宝登录号
                payeeInfo.setName(accountName); // 支付宝账号姓名
                model.setPayeeInfo(payeeInfo);
                // 1.3 构建 AlipayFundTransUniTransferRequest
                AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
                request.setBizModel(model);
                // 执行请求
                AlipayFundTransUniTransferResponse response = client.certificateExecute(request);
                // 处理结果
                if (!response.isSuccess()) {
                    // 当出现 SYSTEM_ERROR, 转账可能成功也可能失败。 返回 WAIT 状态. 后续 job 会轮询
                    if (StringUtils.equalsAny(response.getSubCode(), "SYSTEM_ERROR", "ACQ.SYSTEM_ERROR")) {
                        return TransferResponse.waitingOf(null, transferOrder.getTransferOrderNo(), response);
                    }
                    return TransferResponse.closedOf(response.getSubCode(), response.getSubMsg(), transferOrder.getTransferOrderNo(), response);
                }
                return TransferResponse.successOf(response.getOrderId(), parseTime(response.getTransDate()), response.getOutBizNo(), response);
            }
            case BANK_CARD: {
                Participant payeeInfo = new Participant();
                payeeInfo.setIdentityType("BANKCARD_ACCOUNT");
                // TODO 待实现
                throw new UnsupportedOperationException("待实现");
            }
            default: {
                throw new IllegalStateException("不正确的转账类型: " + transferType);
            }
        }
    }

    // ========== 各种工具方法 ==========

    protected String formatAmount(Integer amount) {
        return String.valueOf(amount / 100.0);
    }

    protected String formatTime(LocalDateTime time) {
        return LocalDateTimeUtil.format(time, NORM_DATETIME_FORMATTER);
    }

    protected LocalDateTime parseTime(String str) {
        return LocalDateTimeUtil.parse(str, NORM_DATETIME_FORMATTER);
    }

}

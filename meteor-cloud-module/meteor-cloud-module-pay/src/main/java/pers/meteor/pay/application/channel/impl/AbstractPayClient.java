package pers.meteor.pay.application.channel.impl;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.common.core.utils.json.JsonUtils;
import pers.meteor.pay.application.channel.PayClient;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.dto.*;
import pers.meteor.pay.infrastructure.channel.exception.PayException;

import java.util.Map;

/**
 * 支付模板
 *
 * @author meteor
 */
@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {
    /**
     * 通道id
     */
    protected final Long channelId;
    /**
     * 通道配置
     */
    protected Config config;

    public AbstractPayClient(Long channelId, Config config) {
        this.channelId = channelId;
        this.config = config;
    }

    /**
     * 初始化客户端
     */
    public final void initClient() {
        doInit();
        log.debug("[pay-client-{}] - 初始化完成", getChannelId());
    }

    /**
     * 自定义初始化客户端的逻辑
     */
    protected void doInit() {
        // empty
    }

    /**
     * 刷新配置
     *
     * @param config /
     */
    public final void refreshConfig(Config config) {
        if (config.equals(this.config)) {
            log.debug("[pay-client-{}] - 配置未更新", getChannelId());
            return;
        }
        log.info("[pay-client-{}] - 配置发生更新，初始化配置", getChannelId());
        this.config = config;
        initClient();
    }

    @Override
    public Long getChannelId() {
        return this.channelId;
    }

    @Override
    public final PayResponse unifiedOrder(PayRequest payRequest) {
        PayResponse payResponse;
        try {
            payResponse = doUnifiedOrder(payRequest);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("发起支付异常-{}：{}", getChannelId(), JsonUtils.toJsonString(payRequest), ex);
            throw buildPayException(ex);
        }

        return payResponse;
    }

    protected abstract PayResponse doUnifiedOrder(PayRequest payRequest) throws Throwable;

    @Override
    public final PayResponse parseOrderNotify(Map<String, String> params) {
        PayResponse payResponse;
        try {
            payResponse = doParseOrderNotify(params);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("解析订单通知异常-{}：{}", getChannelId(), JsonUtils.toJsonString(params), ex);
            throw buildPayException(ex);
        }

        return payResponse;
    }

    protected abstract PayResponse doParseOrderNotify(Map<String, String> params) throws Throwable;

    @Override
    public final PayResponse queryOrder(String outTradeNo) {
        PayResponse payResponse;
        try {
            payResponse = doQueryOrder(outTradeNo);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("订单查询异常-{}：{}", getChannelId(), JsonUtils.toJsonString(outTradeNo), ex);
            throw buildPayException(ex);
        }

        return payResponse;
    }

    protected abstract PayResponse doQueryOrder(String outTradeNo) throws Throwable;

    @Override
    public final RefundResponse unifiedRefund(RefundRequest refundRequest) {
        RefundResponse refundResponse;
        try {
            refundResponse = doUnifiedRefund(refundRequest);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("发起退款异常-{}：{}", getChannelId(), JsonUtils.toJsonString(refundRequest), ex);
            throw buildPayException(ex);
        }

        return refundResponse;
    }

    protected abstract RefundResponse doUnifiedRefund(RefundRequest refundRequest) throws Throwable;

    @Override
    public final RefundResponse parseRefundNotify(Map<String, String> params) {
        RefundResponse refundResponse;
        try {
            refundResponse = doParseRefundNotify(params);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("解析退款通知异常-{}：{}", getChannelId(), JsonUtils.toJsonString(params), ex);
            throw buildPayException(ex);
        }

        return refundResponse;
    }

    protected abstract RefundResponse doParseRefundNotify(Map<String, String> params) throws Throwable;

    @Override
    public final TransferResponse unifiedTransfer(TransferRequest transferRequest) {
        TransferResponse transferResponse;
        try {
            transferResponse = doUnifiedTransfer(transferRequest);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("解析退款通知异常-{}：{}", getChannelId(), JsonUtils.toJsonString(transferRequest), ex);
            throw buildPayException(ex);
        }

        return transferResponse;
    }

    protected abstract TransferResponse doUnifiedTransfer(TransferRequest transferRequest) throws Throwable;

    private PayException buildPayException(Throwable ex) {
        if (ex instanceof PayException) {
            return (PayException) ex;
        }
        throw new PayException(ex);
    }
}

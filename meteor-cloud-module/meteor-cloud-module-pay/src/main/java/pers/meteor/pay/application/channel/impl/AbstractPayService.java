package pers.meteor.pay.application.channel.impl;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.common.core.utils.json.JsonUtils;
import pers.meteor.pay.application.channel.PayService;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.RefundOrder;
import pers.meteor.pay.domain.order.module.TransferOrder;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.module.valueobject.RefundResponse;
import pers.meteor.pay.domain.order.module.valueobject.TransferResponse;
import pers.meteor.pay.infrastructure.channel.exception.PayException;

/**
 * 支付模板
 *
 * @author meteor
 */
@Slf4j
public abstract class AbstractPayService<Config extends PayClient> implements PayService {
    /**
     * 通道id
     */
    protected final Long channelId;
    /**
     * 通道配置
     */
    protected Config config;

    public AbstractPayService(Long channelId, Config config) {
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
    public final PayResponse unifiedOrder(PayOrder payOrder) {
        PayResponse payResponse;
        try {
            payResponse = doUnifiedOrder(payOrder);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("发起支付异常-{}：{}", getChannelId(), JsonUtils.toJsonString(payOrder), ex);
            throw buildPayException(ex);
        }

        return payResponse;
    }

    protected abstract PayResponse doUnifiedOrder(PayOrder payOrder) throws Throwable;

    @Override
    public final PayResponse parseOrderNotify(String body) {
        PayResponse payResponse;
        try {
            payResponse = doParseOrderNotify(body);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("解析订单通知异常-{}：{}", getChannelId(), body, ex);
            throw buildPayException(ex);
        }

        return payResponse;
    }

    protected abstract PayResponse doParseOrderNotify(String body) throws Throwable;

    @Override
    public final PayResponse getOrder(String payOrderNo) {
        PayResponse payResponse;
        try {
            payResponse = doGetOrder(payOrderNo);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("订单查询异常-{}：{}", getChannelId(), JsonUtils.toJsonString(payOrderNo), ex);
            throw buildPayException(ex);
        }

        return payResponse;
    }

    protected abstract PayResponse doGetOrder(String outTradeNo) throws Throwable;

    @Override
    public final RefundResponse unifiedRefund(RefundOrder refundOrder) {
        RefundResponse refundResponse;
        try {
            refundResponse = doUnifiedRefund(refundOrder);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("发起退款异常-{}：{}", getChannelId(), JsonUtils.toJsonString(refundOrder), ex);
            throw buildPayException(ex);
        }

        return refundResponse;
    }

    protected abstract RefundResponse doUnifiedRefund(RefundOrder refundOrder) throws Throwable;

    @Override
    public final RefundResponse parseRefundNotify(String body) {
        RefundResponse refundResponse;
        try {
            refundResponse = doParseRefundNotify(body);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("解析退款通知异常-{}：{}", getChannelId(), body, ex);
            throw buildPayException(ex);
        }

        return refundResponse;
    }

    protected abstract RefundResponse doParseRefundNotify(String body) throws Throwable;

    @Override
    public RefundResponse getRefundOrder(String payOrderNo, String refundOrderNo) {
        RefundResponse refundResponse;
        try {
            refundResponse = doGetRefundOrder(payOrderNo, refundOrderNo);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("获取退款订单异常-{}：{},{}", getChannelId(), payOrderNo, refundOrderNo, ex);
            throw buildPayException(ex);
        }

        return refundResponse;
    }

    protected abstract RefundResponse doGetRefundOrder(String outTradeNo, String outRefundNo) throws Throwable;

    @Override
    public final TransferResponse unifiedTransfer(TransferOrder transferOrder) {
        TransferResponse transferResponse;
        try {
            transferResponse = doUnifiedTransfer(transferOrder);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("解析退款通知异常-{}：{}", getChannelId(), JsonUtils.toJsonString(transferOrder), ex);
            throw buildPayException(ex);
        }

        return transferResponse;
    }

    protected abstract TransferResponse doUnifiedTransfer(TransferOrder transferOrder) throws Throwable;

    private PayException buildPayException(Throwable ex) {
        if (ex instanceof PayException) {
            return (PayException) ex;
        }
        throw new PayException(ex);
    }
}

package pers.meteor.pay.channel.adapter.payclient.mock;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.channel.domain.module.PayClientConfig;
import pers.meteor.pay.channel.domain.module.valueobject.*;
import pers.meteor.pay.channel.domain.service.impl.AbstractPayClient;

import java.util.Map;

/**
 * @author meteor
 */
@Slf4j
public class MockPayClient extends AbstractPayClient<PayClientConfig> {

    public MockPayClient(Long channelId, PayClientConfig config) {
        super(channelId, config);
    }

    @Override
    public PayResponse createOrder(PayRequest payRequest) {
        return null;
    }

    @Override
    public PayResponse parseOrderNotify(Map<String, String> params) {
        return null;
    }

    @Override
    public PayResponse queryOrder(String outTradeNo) {
        return null;
    }

    @Override
    public RefundResponse refund(RefundRequest refundRequest) {
        return null;
    }

    @Override
    public RefundResponse parseRefundResponse(Map<String, String> params) {
        return null;
    }

    @Override
    public TransferResponse transfer(TransferRequest transferRequest) {
        return null;
    }
}

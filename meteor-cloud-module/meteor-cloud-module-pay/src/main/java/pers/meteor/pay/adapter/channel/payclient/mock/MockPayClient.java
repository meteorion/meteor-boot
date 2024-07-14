package pers.meteor.pay.adapter.channel.payclient.mock;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.application.channel.impl.AbstractPayClient;
import pers.meteor.pay.dto.*;

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

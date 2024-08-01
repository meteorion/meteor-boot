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
public class MockPayClient extends AbstractPayClient {

    public MockPayClient(Long channelId, PayClientConfig config) {
        super(channelId, config);
    }

    @Override
    public PayResponse doUnfiedOrder(PayRequest payRequest) {
        return null;
    }

    @Override
    protected PayResponse doParseOrderNotify(Map<String, String> params) throws Throwable {
        return null;
    }

    @Override
    protected PayResponse doQueryOrdery(String outTradeNo) throws Throwable {
        return null;
    }

    @Override
    protected RefundResponse doUnifiedRefund(RefundRequest refundRequest) throws Throwable {
        return null;
    }

    @Override
    protected RefundResponse doParseRefundNotify(Map<String, String> params) throws Throwable {
        return null;
    }

    @Override
    protected TransferResponse doUnifiedTransfer(TransferRequest transferRequest) throws Throwable {
        return null;
    }
}

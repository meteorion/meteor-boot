package pers.meteor.pay.adapter.channel.payclient.mock;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.application.channel.impl.AbstractPayClient;
import pers.meteor.pay.domain.channel.module.valueobject.NonePayClientConfig;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.RefundOrder;
import pers.meteor.pay.domain.order.module.TransferOrder;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.module.valueobject.RefundResponse;
import pers.meteor.pay.domain.order.module.valueobject.TransferResponse;
import pers.meteor.pay.dto.*;

import java.util.Map;

/**
 * @author meteor
 */
@Slf4j
public class MockPayClient extends AbstractPayClient<NonePayClientConfig> {

    public MockPayClient(Long channelId, NonePayClientConfig config) {
        super(channelId, config);
    }

    @Override
    public PayResponse doUnifiedOrder(PayOrder payOrder) {
        return null;
    }

    @Override
    protected PayResponse doParseOrderNotify(String body) throws Throwable {
        return null;
    }


    @Override
    protected PayResponse doGetOrder(String outTradeNo) throws Throwable {
        return null;
    }

    @Override
    protected RefundResponse doUnifiedRefund(RefundOrder refundOrder) throws Throwable {
        return null;
    }

    @Override
    protected RefundResponse doParseRefundNotify(String body) throws Throwable {
        return null;
    }

    @Override
    protected RefundResponse doGetRefund(String outTradeNo, String outRefundNo) throws Throwable {
        return null;
    }

    @Override
    protected TransferResponse doUnifiedTransfer(TransferOrder transferOrder) throws Throwable {
        return null;
    }
}

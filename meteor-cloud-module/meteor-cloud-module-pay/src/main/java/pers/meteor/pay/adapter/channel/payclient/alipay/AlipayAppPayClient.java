package pers.meteor.pay.adapter.channel.payclient.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;


/**
 * 支付宝【App 支付】的 PayClient 实现类
 *
 * @author meteor
 * @see <a href="https://opendocs.alipay.com/open/02e7gq">App 支付</a>
 */
@Slf4j
public class AlipayAppPayClient extends AbstractAlipayPayClient {

    public AlipayAppPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, config);
    }

    @Override
    public PayResponse doUnifiedOrder(PayOrder payOrder) throws AlipayApiException {
        // 1.1 构建 AlipayTradeAppPayModel 请求
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // ① 通用的参数
        model.setOutTradeNo(payOrder.getOrderNo());
        model.setSubject(payOrder.getGoods().getDescription());
        model.setBody(payOrder.getGoods() + "test");
        model.setTotalAmount(formatAmount(payOrder.getAmount()));
        model.setTimeExpire(formatTime(payOrder.getExpireTime()));
        model.setProductCode("QUICK_MSECURITY_PAY"); // 销售产品码：无线快捷支付产品
        // ② 个性化的参数【无】
        // ③ 支付宝扫码支付只有一种展示
        // 1.2 构建 AlipayTradePrecreateRequest 请求
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(payOrder.getNotifyUrl());
        request.setReturnUrl(payOrder.getReturnUrl());

        // 2.1 执行请求
        AlipayTradeAppPayResponse response = client.sdkExecute(request);
        // 2.2 处理结果
        if (!response.isSuccess()) {
            return buildClosedPayResponse(payOrder, response);
        }
        return PayResponse.waitingOf(DisplayModeEnum.APP, response.getBody(), payOrder.getOrderNo(), response);
    }
}

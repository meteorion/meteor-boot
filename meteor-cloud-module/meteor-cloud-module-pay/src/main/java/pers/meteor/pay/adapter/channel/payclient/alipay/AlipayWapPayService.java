package pers.meteor.pay.adapter.channel.payclient.alipay;

import cn.hutool.http.Method;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.core.utils.json.JsonUtils;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

/**
 * 支付宝【Wap 网站】的 PayClient 实现类
 *
 * @author meteor
 * @see <a href="https://opendocs.alipay.com/apis/api_1/alipay.trade.wap.pay">手机网站支付接口</a>
 */
@Slf4j
public class AlipayWapPayService extends AbstractAlipayPayService {

    public AlipayWapPayService(Long channelId, AlipayPayClient config) {
        super(channelId, config);
    }

    @Override
    public PayResponse doUnifiedOrder(PayOrder payOrder) throws AlipayApiException {
        // 1.1 构建 AlipayTradeWapPayModel 请求
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        // ① 通用的参数
        model.setOutTradeNo(payOrder.getOrderNo());
        model.setSubject(payOrder.getGoods().getDescription());
        model.setBody(JsonUtils.toJsonString(payOrder.getGoods()));
        model.setTotalAmount(formatAmount(payOrder.getAmount()));
        model.setProductCode("QUICK_WAP_PAY"); // 销售产品码. 目前 Wap 支付场景下仅支持 QUICK_WAP_PAY
        // ② 个性化的参数【无】
        // ③ 支付宝 Wap 支付只有一种展示：URL
        DisplayModeEnum displayMode = DisplayModeEnum.URL;

        // 1.2 构建 AlipayTradeWapPayRequest 请求
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(payOrder.getNotifyUrl());
        request.setReturnUrl(payOrder.getReturnUrl());
        model.setQuitUrl(payOrder.getReturnUrl());

        // 2.1 执行请求
        AlipayTradeWapPayResponse response = client.pageExecute(request, Method.GET.name());
        // 2.2 处理结果
        if (!response.isSuccess()) {
            return buildClosedPayResponse(payOrder, response);
        }
        return PayResponse.waitingOf(displayMode, response.getBody(), payOrder.getOrderNo(), response);
    }
}

package pers.meteor.pay.adapter.channel.payclient.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.core.utils.json.JsonUtils;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

import java.util.Objects;

import static pers.meteor.pay.adapter.channel.payclient.alipay.AlipayPayClient.MODE_CERTIFICATE;


/**
 * 支付宝【扫码支付】的 PayClient 实现类
 *
 * @author meteor
 * @see <a href="https://opendocs.alipay.com/apis/02890k">扫码支付</a>
 */
@Slf4j
public class AlipayQrPayClient extends AbstractAlipayPayClient {

    public AlipayQrPayClient(Long channelId, AlipayPayClient config) {
        super(channelId, config);
    }

    @Override
    public PayResponse doUnifiedOrder(PayOrder payOrder) throws AlipayApiException {
        // 1.1 构建 AlipayTradePrecreateModel 请求
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        // ① 通用的参数
        model.setOutTradeNo(payOrder.getOrderNo());
        model.setSubject(payOrder.getGoods().getDescription());
        model.setBody(JsonUtils.toJsonString(payOrder.getGoods()));
        model.setTotalAmount(formatAmount(payOrder.getAmount()));
        model.setProductCode("FACE_TO_FACE_PAYMENT"); // 销售产品码. 目前扫码支付场景下仅支持 FACE_TO_FACE_PAYMENT
        // ② 个性化的参数【无】
        // ③ 支付宝扫码支付只有一种展示，考虑到前端可能希望二维码扫描后，手机打开
        DisplayModeEnum displayMode = DisplayModeEnum.QR_CODE;

        // 1.2 构建 AlipayTradePrecreateRequest 请求
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setNotifyUrl(payOrder.getNotifyUrl());
        request.setReturnUrl(payOrder.getReturnUrl());

        // 2.1 执行请求
        AlipayTradePrecreateResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) {
            // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        // 2.2 处理结果
        if (!response.isSuccess()) {
            return buildClosedPayResponse(payOrder, response);
        }
        return PayResponse.waitingOf(displayMode, response.getQrCode(), payOrder.getOrderNo(), response);
    }
}

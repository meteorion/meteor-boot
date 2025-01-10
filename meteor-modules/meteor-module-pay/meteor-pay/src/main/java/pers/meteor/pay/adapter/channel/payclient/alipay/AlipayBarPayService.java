package pers.meteor.pay.adapter.channel.payclient.alipay;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.utils.json.JsonUtils;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

import java.time.LocalDateTime;
import java.util.Objects;

import static pers.meteor.pay.adapter.channel.payclient.alipay.AlipayPayClient.MODE_CERTIFICATE;

/**
 * 支付宝【条码支付】的 PayClient 实现类
 *
 * @author meteor
 * @see <a href="https://opendocs.alipay.com/open/194/105072">当面付</a>
 */
@Slf4j
public class AlipayBarPayService extends AbstractAlipayPayService {

    public AlipayBarPayService(Long channelId, AlipayPayClient config) {
        super(channelId, config);
    }

    @Override
    public PayResponse doUnifiedOrder(PayOrder payOrder) throws AlipayApiException {
        String authCode = payOrder.getMetadata("auth_code");
        if (StrUtil.isEmpty(authCode)) {
            throw new ServiceException("条形码不能为空");
        }

        // 1.1 构建 AlipayTradePayModel 请求
        AlipayTradePayModel model = new AlipayTradePayModel();
        // ① 通用的参数
        model.setOutTradeNo(payOrder.getOrderNo());
        model.setSubject(payOrder.getGoods().getDescription());
        model.setBody(JsonUtils.toJsonString(payOrder.getGoods()));
        model.setTotalAmount(formatAmount(payOrder.getAmount()));
        model.setScene("bar_code"); // 当面付条码支付场景
        // ② 个性化的参数
        model.setAuthCode(authCode);
        // ③ 支付宝条码支付只有一种展示
        DisplayModeEnum displayMode = DisplayModeEnum.BAR_CODE;

        // 1.2 构建 AlipayTradePayRequest 请求
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(payOrder.getNotifyUrl());
        request.setReturnUrl(payOrder.getReturnUrl());

        // 2.1 执行请求
        AlipayTradePayResponse response;
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
        if ("10000".equals(response.getCode())) { // 免密支付
            LocalDateTime successTime = LocalDateTimeUtil.of(response.getGmtPayment());
            PayResponse payResponse = PayResponse.successOf(response.getTradeNo(), response.getBuyerUserId(), successTime, response.getOutTradeNo(), response);
            payResponse.setDisplayMode(displayMode);
            payResponse.setDisplayContent("");
            return payResponse;
        }
        // 大额支付，需要用户输入密码，所以返回 waiting。此时，前端一般会进行轮询
        return PayResponse.waitingOf(displayMode, "", payOrder.getOrderNo(), response);
    }
}

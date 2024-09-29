package pers.meteor.pay.application.order.assembler;

import org.apache.commons.collections4.MapUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.cmd.PayOrderSubmitCmd;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.valueobject.Fee;
import pers.meteor.pay.domain.order.module.valueobject.Goods;
import pers.meteor.pay.domain.order.module.valueobject.Payer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author meteor
 */
@Mapper
public interface OrderAssembler extends BaseOrderAssembler {
    OrderAssembler INSTANCE = Mappers.getMapper(OrderAssembler.class);

    default PayOrder toPayOrder(PayOrderSubmitCmd payRequestDto) {
        Map<String, Object> attachData = Optional.ofNullable(payRequestDto.getAttachData()).orElse(new HashMap<>());

        PayOrder payOrder = new PayOrder();
        payOrder.setMerchantOrderNo(payRequestDto.getMerchantOrderId());
        payOrder.setTradeChannelId(payRequestDto.getPayClientId());
        payOrder.setAmount(payRequestDto.getAmount());

        Fee fee = new Fee();
        fee.setTradeFee(payRequestDto.getFee());
        payOrder.setFee(fee);
        payOrder.setCurrency(getCurrencyType(payRequestDto.getCurrency()));

        Payer payer = new Payer();
        payer.setClientIp(payRequestDto.getUserIp());
        payer.setOpenId(MapUtils.getString(attachData, "openid"));
        payOrder.setPayer(payer);

        Goods goods = new Goods();
        goods.setCostPrice(payRequestDto.getCostPrice());
        goods.setTag(payRequestDto.getTag());
        goods.setDescription(payRequestDto.getDescription());
        goods.setInvoiceId(payRequestDto.getInvoiceId());
        payOrder.setGoods(goods);

        return payOrder;
    }
}

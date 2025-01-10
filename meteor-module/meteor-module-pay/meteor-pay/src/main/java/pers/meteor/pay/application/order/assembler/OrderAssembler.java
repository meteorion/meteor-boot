package pers.meteor.pay.application.order.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.cmd.PayOrderCreateCmd;
import pers.meteor.pay.cmd.PayOrderSubmitCmd;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.valueobject.Fee;
import pers.meteor.pay.domain.order.module.valueobject.Goods;
import pers.meteor.pay.domain.order.module.valueobject.Payer;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppSimpleRespVO;

import java.time.LocalDateTime;

/**
 * @author meteor
 */
@Mapper
public interface OrderAssembler extends BaseOrderAssembler {
    OrderAssembler INSTANCE = Mappers.getMapper(OrderAssembler.class);

    default PayOrder toPayOrder(PayOrderCreateCmd payOrderCreateCmd, PayAppSimpleRespVO payApp) {
        PayOrder payOrder = new PayOrder();
        payOrder.setMerchantOrderNo(payOrderCreateCmd.getMerchantOrderId());
        payOrder.setAmount(payOrderCreateCmd.getAmount());

        Fee fee = new Fee();
        fee.setTradeFee(payOrderCreateCmd.getFee());
        payOrder.setFee(fee);
        payOrder.setCurrency(getCurrencyType(payOrderCreateCmd.getCurrency()));

        Payer payer = new Payer();
        payer.setClientIp(payOrderCreateCmd.getUserIp());
        payOrder.setPayer(payer);

        Goods goods = new Goods();
        goods.setCostPrice(payOrderCreateCmd.getCostPrice());
        goods.setTag(payOrderCreateCmd.getTag());
        goods.setDescription(payOrderCreateCmd.getDescription());
        goods.setInvoiceId(payOrderCreateCmd.getInvoiceId());
        payOrder.setGoods(goods);

        if (payApp.getExpiry() != null) {
            payOrder.setExpireTime(LocalDateTime.now().plusSeconds(payApp.getExpiry()));
        }

        return payOrder;
    }

    default PayOrder toSubmitOrder(PayOrder payOrder, PayOrderSubmitCmd payOrderSubmitCmd) {
        return new PayOrder();
    }
}

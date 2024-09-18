package pers.meteor.pay.application.order.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.dto.PayRequestDto;

/**
 * @author meteor
 */
@Mapper
public interface OrderAssembler extends BaseOrderAssembler {
    OrderAssembler INSTANCE = Mappers.getMapper(OrderAssembler.class);

    default PayOrder toPayOrder(PayRequestDto payRequestDto) {


        return new PayOrder();
    }
}

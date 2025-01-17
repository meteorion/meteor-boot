package pers.meteor.pay.application.channel.query;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.common.enums.SwitchStatusEnum;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.pay.application.channel.PayChannelQueryService;
import pers.meteor.pay.application.channel.asselmber.PayAppAssembler;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayAppMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayChannelMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayClientMapper;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayAppEntity;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelEntity;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientPo;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppSimpleRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayChannelRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayClientRespVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Service
@RequiredArgsConstructor
public class PayChannelQueryServiceImpl implements PayChannelQueryService {
    private final PayAppMapper payChannelMapper;
    private final PayChannelMapper payChannelConfigMapper;
    private final PayClientMapper payClientConfigMapper;

    @Override
    public List<PayAppSimpleRespVO> listPayApp() {
        List<PayAppEntity> payChannelPos = payChannelMapper.selectList(Wrappers.emptyWrapper());
        return payChannelPos.stream().map(PayAppAssembler.INSTANCE::toPayAppSimpleResp).collect(Collectors.toList());
    }

    @Override
    public PayAppRespVO getPayApp(Long appId) {
        PayAppEntity payAppPo = payChannelMapper.selectById(appId);
        if (payAppPo != null) {
            PayAppRespVO payAppResp = PayAppAssembler.INSTANCE.toPayAppResp(payAppPo);
            List<PayChannelEntity> payChannelPos = payChannelConfigMapper.selectPayChannelList(appId);
            ArrayList<PayChannelRespVO> payChannelResps = new ArrayList<>();
            for (PayChannelEntity payChannelPo : payChannelPos) {
                PayChannelRespVO payChannelResp = PayAppAssembler.INSTANCE.toPayChannelResp(payChannelPo);
                PayClientPo payClientPo = payClientConfigMapper.selectByChannelId(payChannelPo.getChannelId());
                PayClientRespVO payClientResp = PayAppAssembler.INSTANCE.toPayClientResp(payClientPo);
                payChannelResp.setPayClient(payClientResp);
                payChannelResps.add(payChannelResp);
            }
            payAppResp.setPayChannels(payChannelResps);
            return payAppResp;
        }

        return null;
    }

    @Override
    public PayAppSimpleRespVO validPayApp(Long appId) {
        PayAppEntity payAppPo = payChannelMapper.selectById(appId);
        if (payAppPo == null) {
            throw new ServiceException("支付运用不存在");
        }
        if (SwitchStatusEnum.CLOSE.getCode() == payAppPo.getStatus()) {
            throw new ServiceException("支付运用已禁用");
        }
        return PayAppAssembler.INSTANCE.toPayAppSimpleResp(payAppPo);
    }
}

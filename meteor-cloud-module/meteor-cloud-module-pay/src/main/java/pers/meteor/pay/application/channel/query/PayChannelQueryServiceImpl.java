package pers.meteor.pay.application.channel.query;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.pay.application.channel.PayChannelQueryService;
import pers.meteor.pay.application.channel.asselmber.PayAppAssembler;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayAppMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayChannelMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayClientMapper;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayAppPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientPo;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppSimpleRespVO;

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
    public List<PayAppSimpleRespVO> listPayChannel() {
        List<PayAppPo> payChannelPos = payChannelMapper.selectList(Wrappers.emptyWrapper());
        return payChannelPos.stream().map(PayAppAssembler.INSTANCE::toPayAppSimpleResp).collect(Collectors.toList());
    }

    @Override
    public PayAppRespVO getPayChannel(Long payChannelId) {
        PayAppPo payChannelPo = payChannelMapper.selectById(payChannelId);
        if (payChannelPo != null) {
            List<PayChannelPo> payChannelConfigPos = payChannelConfigMapper.selectPayChannelList(payChannelId);
            List<PayClientPo> payClientConfigPos = payClientConfigMapper.selectPayClientList(payChannelId);

            return PayAppAssembler.INSTANCE.toPayAppResp(payChannelPo, payChannelConfigPos, payClientConfigPos);
        }

        return new PayAppRespVO();
    }
}

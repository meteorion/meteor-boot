package pers.meteor.pay.application.channel.query;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.pay.application.channel.PayChannelQueryService;
import pers.meteor.pay.application.channel.asselmber.PayChannelAssembler;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayChannelConfigMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayChannelMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayClientConfigMapper;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPO;
import pers.meteor.pay.interfaces.channel.vo.PayChannelRespVO;
import pers.meteor.pay.interfaces.channel.vo.PayChannelSimpleRespVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Service
@RequiredArgsConstructor
public class PayChannelQueryServiceImpl implements PayChannelQueryService {
    private final PayChannelMapper payChannelMapper;
    private final PayChannelConfigMapper payChannelConfigMapper;
    private final PayClientConfigMapper payClientConfigMapper;

    @Override
    public List<PayChannelSimpleRespVO> listPayChannel() {
        List<PayChannelPO> payChannelPos = payChannelMapper.selectList(Wrappers.emptyWrapper());
        return payChannelPos.stream().map(PayChannelAssembler.INSTANCE::toPayChannelSimpleResp).collect(Collectors.toList());
    }

    @Override
    public PayChannelRespVO getPayChannel(Long payChannelId) {
        PayChannelPO payChannelPo = payChannelMapper.selectById(payChannelId);
        if (payChannelPo != null) {
            List<PayChannelConfigPO> payChannelConfigPos = payChannelConfigMapper.selectPayChannelConfigList(payChannelId);
            List<PayClientConfigPO> payClientConfigPos = payClientConfigMapper.selectPayClientConfigList(payChannelId);

            return PayChannelAssembler.INSTANCE.toPayChannelResp(payChannelPo, payChannelConfigPos, payClientConfigPos);
        }

        return new PayChannelRespVO();
    }
}

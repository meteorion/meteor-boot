package pers.meteor.pay.infrastructure.channel.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.domain.channel.repository.PayChannelRepository;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayChannelConfigMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayChannelMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayClientConfigMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapstruct.PayChannelMapstruct;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPo;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Repository
@RequiredArgsConstructor
public class PayChannelRepositoryImpl implements PayChannelRepository {
    private final PayChannelMapper payChannelMapper;
    private final PayChannelConfigMapper payChannelConfigMapper;
    private final PayClientConfigMapper payClientConfigMapper;

    @Override
    public Long save(PayChannel payChannel) {
        PayChannelPO payChannelPo = PayChannelMapstruct.INSTANCE.toPayChannelPo(payChannel);
        Long payChannelId = payChannelPo.getPayChannelId();
        if (payChannelId == null) {
            payChannelMapper.updateById(payChannelPo);
        } else {
            payChannelMapper.insert(payChannelPo);
        }
        payChannelId = payChannelPo.getPayChannelId();
        payChannel.setPayChannelId(payChannelId);

        saveChannelConfigs(payChannelId, payChannel);

        return payChannelId;
    }

    @Override
    public void updateChannelStatus(Long payChannelId, SwitchStatusEnum switchStatus) {
        PayChannelPO payChannelPo = new PayChannelPO();
        payChannelPo.setPayChannelId(payChannelId);
        payChannelPo.setStatus(switchStatus.getCode());
        payChannelMapper.updateById(payChannelPo);
    }

    @Override
    public void saveChannelConfig(ChannelConfig channelConfig) {
        Long payChannelId = channelConfig.getPayChannelId();
        PayChannelConfigPo payClientConfigPo = PayChannelMapstruct.INSTANCE.toChannelConfigPo(channelConfig);
        PayChannelConfigPo payChannelConfigDbPo = payChannelConfigMapper.selectOne(payChannelId, payClientConfigPo.getChannelType());
        if (payChannelConfigDbPo != null) {
            payClientConfigPo.setConfigId(payChannelConfigDbPo.getConfigId());
            payChannelConfigMapper.updateById(payClientConfigPo);
        } else {
            payChannelConfigMapper.insert(payClientConfigPo);
        }
        // 保存费率
        saveChannelRate(payChannelId, channelConfig.getChannelRate());
        // 保存客户端配置
        saveClientConfig(payChannelId, channelConfig.getPayClientConfig());
    }

    @Override
    public void saveChannelConfigs(Long payChannelId, PayChannel payChannel) {
        EnumMap<PayChannelEnum, ChannelConfig> channelConfigs = payChannel.getChannelConfigs();
        List<PayChannelConfigPo> payClientConfigPos = payChannelConfigMapper.selectPayChannelConfigList(payChannelId);
        Map<String, PayChannelConfigPo> clientConfigPoMap = payClientConfigPos.stream().collect(Collectors.toMap(PayChannelConfigPo::getChannelType, po -> po));

        for (Map.Entry<PayChannelEnum, ChannelConfig> entry : channelConfigs.entrySet()) {
            PayChannelEnum channelType = entry.getKey();
            ChannelConfig channelConfig = entry.getValue();
            channelConfig.setPayChannelId(payChannelId);

            PayChannelConfigPo payClientConfigPo = clientConfigPoMap.get(channelType.getCode());
            PayChannelConfigPo newChannelConfig = PayChannelMapstruct.INSTANCE.toChannelConfigPo(channelConfig);
            if (payClientConfigPo != null) {
                newChannelConfig.setConfigId(payClientConfigPo.getConfigId());
                payChannelConfigMapper.updateById(newChannelConfig);
            } else {
                payChannelConfigMapper.insert(newChannelConfig);
            }
            // 保存费率
            saveChannelRate(payChannelId, channelConfig.getChannelRate());
            // 保存客户端配置
            saveClientConfig(payChannelId, channelConfig.getPayClientConfig());
        }
    }

    @Override
    public void saveChannelRate(Long payChanneConfigId, ChannelRate channelRate) {
        if (channelRate == null) {
            return;
        }
        PayChannelConfigPo channelRatePo = PayChannelMapstruct.INSTANCE.toChannelConfigPo(payChanneConfigId, channelRate);
        if (channelRatePo.getConfigId() != null) {
            payChannelConfigMapper.updateById(channelRatePo);
        } else {
            payChannelConfigMapper.insert(channelRatePo);
        }
    }

    @Override
    public void saveClientConfig(Long payChanneId, PayClientConfig clientConfig) {
        if (clientConfig == null) {
            return;
        }
        PayClientConfigPo clientConfigPo = PayChannelMapstruct.INSTANCE.toPayClinetConfigPo(clientConfig);
        PayClientConfigPo clientConfigPoDb = payClientConfigMapper.selectOne(payChanneId, clientConfigPo.getChannelType());
        if (clientConfigPoDb != null) {
            clientConfigPo.setChannelConfigId(clientConfigPoDb.getChannelConfigId());
            payClientConfigMapper.updateById(clientConfigPo);
        } else {
            payClientConfigMapper.insert(clientConfigPo);
        }
    }

    @Override
    public PayChannel selectById(Long payChannelId) {
        PayChannelPO payChannelPo = payChannelMapper.selectById(payChannelId);
        return fill(payChannelPo);
    }

    @Override
    public PayChannel selectByName(String name) {
        PayChannelPO payChannelPo = payChannelMapper.selectOne(PayChannelPO::getName, name);
        return fill(payChannelPo);
    }

    @Override
    public List<PayChannel> selectByCode(String code) {
        List<PayChannelPO> payChannelPos = payChannelMapper.selectList(PayChannelPO::getCode, code);
        return payChannelPos.stream().map(this::fill).collect(Collectors.toList());
    }

    @Override
    public PayClientConfig selectPayClientConfig(Long payClientlId) {
        PayClientConfigPo payClientConfigPo = payClientConfigMapper.selectById(payClientlId);
        return PayChannelMapstruct.INSTANCE.toPayClientConfg(payClientConfigPo);
    }

    @Override
    public ChannelConfig selectPayChannelConfig(Long payChannelConfigId) {
        PayChannelConfigPo payChannelConfigPo = payChannelConfigMapper.selectById(payChannelConfigId);
        if (payChannelConfigPo == null) {
            return null;
        }
        PayClientConfigPo payClientConfigPo = payClientConfigMapper.selectOne(payChannelConfigPo.getPayChannelId(),
                payChannelConfigPo.getChannelType());
        return PayChannelMapstruct.INSTANCE.toChannelConfig(payChannelConfigPo, payClientConfigPo);
    }

    @Override
    public ChannelConfig selectPayChannelConfig(Long payChannelId, PayChannelEnum channelType) {
        PayChannelConfigPo payChannelConfigPo = payChannelConfigMapper.selectOne(payChannelId, channelType.getCode());
        if (payChannelConfigPo == null) {
            return null;
        }
        PayClientConfigPo payClientConfigPo = payClientConfigMapper.selectOne(payChannelConfigPo.getPayChannelId(),
                payChannelConfigPo.getChannelType());
        return PayChannelMapstruct.INSTANCE.toChannelConfig(payChannelConfigPo, payClientConfigPo);
    }

    /**
     * 填充payChannel属性
     *
     * @param payChannelPo /
     * @return /
     */
    private PayChannel fill(PayChannelPO payChannelPo) {
        if (payChannelPo == null) {
            return null;
        }
        Long payChannelId = payChannelPo.getPayChannelId();
        // 同时查询通道配置
        List<PayChannelConfigPo> payChannelConfigPos = payChannelConfigMapper.selectPayChannelConfigList(payChannelId);
        // 查询通道配置
        List<PayClientConfigPo> payClientConfigPos = payClientConfigMapper.selectPayClientConfigList(payChannelId);

        return PayChannelMapstruct.INSTANCE.toPayChannel(payChannelPo, payChannelConfigPos, payClientConfigPos);
    }

}

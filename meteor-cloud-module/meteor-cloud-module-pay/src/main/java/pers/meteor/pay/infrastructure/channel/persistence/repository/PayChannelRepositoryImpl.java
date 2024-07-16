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
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPO;

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
    public Long saveChannelConfig(ChannelConfig channelConfig) {
        Long payChannelId = channelConfig.getPayChannelId();
        PayChannelConfigPO payClientConfigPo = PayChannelMapstruct.INSTANCE.toChannelConfigPo(channelConfig);
        PayChannelConfigPO payChannelConfigDbPo = payChannelConfigMapper.selectOne(payChannelId, payClientConfigPo.getChannelType());
        if (payChannelConfigDbPo != null) {
            payClientConfigPo.setChannelConfigId(payChannelConfigDbPo.getChannelConfigId());
            payChannelConfigMapper.updateById(payClientConfigPo);
        } else {
            payChannelConfigMapper.insert(payClientConfigPo);
        }
        // 保存费率
        saveChannelRate(payChannelId, channelConfig.getChannelRate());
        // 保存客户端配置
        saveClientConfig(payChannelId, channelConfig.getPayClientConfig());

        return payClientConfigPo.getChannelConfigId();
    }

    @Override
    public void saveChannelConfigs(Long payChannelId, PayChannel payChannel) {
        EnumMap<PayChannelEnum, ChannelConfig> channelConfigs = payChannel.getChannelConfigs();
        List<PayChannelConfigPO> payClientConfigPos = payChannelConfigMapper.selectPayChannelConfigList(payChannelId);
        Map<String, PayChannelConfigPO> clientConfigPoMap = payClientConfigPos.stream().collect(Collectors.toMap(PayChannelConfigPO::getChannelType, po -> po));

        for (Map.Entry<PayChannelEnum, ChannelConfig> entry : channelConfigs.entrySet()) {
            PayChannelEnum channelType = entry.getKey();
            ChannelConfig channelConfig = entry.getValue();
            channelConfig.setPayChannelId(payChannelId);

            PayChannelConfigPO payClientConfigPo = clientConfigPoMap.get(channelType.getCode());
            PayChannelConfigPO newChannelConfig = PayChannelMapstruct.INSTANCE.toChannelConfigPo(channelConfig);
            if (payClientConfigPo != null) {
                newChannelConfig.setChannelConfigId(payClientConfigPo.getChannelConfigId());
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
        PayChannelConfigPO channelRatePo = PayChannelMapstruct.INSTANCE.toChannelConfigPo(payChanneConfigId, channelRate);
        if (channelRatePo.getChannelConfigId() != null) {
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
        PayClientConfigPO clientConfigPo = PayChannelMapstruct.INSTANCE.toPayClinetConfigPo(clientConfig);
        PayClientConfigPO clientConfigPoDb = payClientConfigMapper.selectOne(payChanneId, clientConfigPo.getChannelType());
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
        PayClientConfigPO payClientConfigPo = payClientConfigMapper.selectById(payClientlId);
        return PayChannelMapstruct.INSTANCE.toPayClientConfg(payClientConfigPo);
    }

    @Override
    public ChannelConfig selectPayChannelConfig(Long payChannelConfigId) {
        PayChannelConfigPO payChannelConfigPo = payChannelConfigMapper.selectById(payChannelConfigId);
        if (payChannelConfigPo == null) {
            return null;
        }
        PayClientConfigPO payClientConfigPo = payClientConfigMapper.selectOne(payChannelConfigPo.getPayChannelId(),
                payChannelConfigPo.getChannelType());
        return PayChannelMapstruct.INSTANCE.toChannelConfig(payChannelConfigPo, payClientConfigPo);
    }

    @Override
    public ChannelConfig selectPayChannelConfig(Long payChannelId, PayChannelEnum channelType) {
        PayChannelConfigPO payChannelConfigPo = payChannelConfigMapper.selectOne(payChannelId, channelType.getCode());
        if (payChannelConfigPo == null) {
            return null;
        }
        PayClientConfigPO payClientConfigPo = payClientConfigMapper.selectOne(payChannelConfigPo.getPayChannelId(),
                payChannelConfigPo.getChannelType());
        return PayChannelMapstruct.INSTANCE.toChannelConfig(payChannelConfigPo, payClientConfigPo);
    }

    @Override
    public void delete(Long payChannelId) {
        payChannelMapper.deleteById(payChannelId);
        // 删除通道配置
        payChannelConfigMapper.deleteByPayChannelId(payChannelId);
        payClientConfigMapper.deleteByPayChannelId(payChannelId);
    }

    @Override
    public void deleteChannelConfig(Long payChannelConfigId) {
        payChannelConfigMapper.deleteById(payChannelConfigId);
        payClientConfigMapper.deleteByChannelConfigId(payChannelConfigId);
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
        List<PayChannelConfigPO> payChannelConfigPos = payChannelConfigMapper.selectPayChannelConfigList(payChannelId);
        // 查询通道配置
        List<PayClientConfigPO> payClientConfigPos = payClientConfigMapper.selectPayClientConfigList(payChannelId);

        return PayChannelMapstruct.INSTANCE.toPayChannel(payChannelPo, payChannelConfigPos, payClientConfigPos);
    }

}

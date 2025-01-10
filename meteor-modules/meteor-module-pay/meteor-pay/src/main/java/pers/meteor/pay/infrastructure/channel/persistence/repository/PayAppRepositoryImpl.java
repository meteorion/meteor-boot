package pers.meteor.pay.infrastructure.channel.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pers.meteor.common.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.PayApp;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.domain.channel.repository.PayAppRepository;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayAppMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayChannelMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapper.PayClientMapper;
import pers.meteor.pay.infrastructure.channel.persistence.mapstruct.PayAppMapstruct;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayAppPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientPo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Repository
@RequiredArgsConstructor
public class PayAppRepositoryImpl implements PayAppRepository {
    private final PayAppMapper payAppMapper;
    private final PayChannelMapper payChannelMapper;
    private final PayClientMapper payClientMapper;

    @Override
    public Long save(PayApp payApp) {
        PayAppPo payAppPo = PayAppMapstruct.INSTANCE.toPayAppPo(payApp);
        Long appId = payAppPo.getAppId();
        if (appId == null) {
            payAppMapper.insert(payAppPo);
        } else {
            payAppMapper.updateById(payAppPo);
        }
        appId = payAppPo.getAppId();
        payApp.setAppId(appId);

        if (payApp.getPayChannels() != null) {
            savePayChannels(payApp.getPayChannels().values());
        }

        return appId;
    }

    @Override
    public void updatePayAppStatus(Long appId, SwitchStatusEnum switchStatus) {
        PayAppPo payAppPo = new PayAppPo();
        payAppPo.setAppId(appId);
        payAppPo.setStatus(switchStatus.getCode());
        payAppMapper.updateById(payAppPo);
    }

    @Override
    public Long savePayChannel(PayChannel payChannel) {
        Long appId = payChannel.getAppId();
        PayChannelPo payChannelPo = PayAppMapstruct.INSTANCE.toPayAppPo(payChannel);
        PayChannelPo payChannelDbPo = payChannelMapper.selectOne(appId, payChannelPo.getChannelType());
        if (payChannelDbPo != null) {
            payChannelPo.setChannelId(payChannelDbPo.getChannelId());
            payChannelMapper.updateById(payChannelPo);
        } else {
            payChannelMapper.insert(payChannelPo);
        }
        // 保存客户端配置
        savePayClient(payChannel.getPayClient());

        return payChannelPo.getChannelId();
    }

    @Override
    public void savePayChannels(Collection<PayChannel> payChannels) {
        for (PayChannel payChannel : payChannels) {
            PayChannelPo newPayChannelPo = PayAppMapstruct.INSTANCE.toPayAppPo(payChannel);
            PayChannelPo payChannelPo = payChannelMapper.selectOne(payChannel.getAppId(), newPayChannelPo.getChannelType());
            if (payChannelPo != null) {
                newPayChannelPo.setChannelId(payChannelPo.getChannelId());
                payChannelMapper.updateById(newPayChannelPo);
            } else {
                payChannelMapper.insert(newPayChannelPo);
            }
            // 保存费率
            saveRate(payChannel.getChannelId(), payChannel.getChannelRate());
            // 保存客户端配置
            savePayClient(payChannel.getPayClient());
        }
    }

    @Override
    public void saveRate(Long channelId, Rate channelRate) {
        if (channelRate == null) {
            return;
        }
        PayChannelPo channelRatePo = PayAppMapstruct.INSTANCE.toPayAppPo(channelId, channelRate);
        if (channelRatePo.getChannelId() != null) {
            payChannelMapper.updateById(channelRatePo);
        } else {
            payChannelMapper.insert(channelRatePo);
        }
    }

    @Override
    public void savePayClient(PayClient clientConfig) {
        if (clientConfig == null) {
            return;
        }
        PayClientPo clientConfigPo = PayAppMapstruct.INSTANCE.toPayClientPo(clientConfig);
        PayClientPo clientConfigPoDb = payClientMapper.selectOne(clientConfig.getChannelId(), clientConfigPo.getChannelType());
        if (clientConfigPoDb != null) {
            clientConfigPo.setClientId(clientConfigPoDb.getClientId());
            payClientMapper.updateById(clientConfigPo);
        } else {
            payClientMapper.insert(clientConfigPo);
        }
    }

    @Override
    public PayApp selectById(Long appId) {
        PayAppPo payAppPo = payAppMapper.selectById(appId);
        return fill(payAppPo);
    }

    @Override
    public PayApp selectByName(String name) {
        PayAppPo payChannelPo = payAppMapper.selectOne(PayAppPo::getName, name);
        return fill(payChannelPo);
    }

    @Override
    public List<PayApp> selectByCode(String code) {
        List<PayAppPo> payAppPos = payAppMapper.selectList(PayAppPo::getCode, code);
        return payAppPos.stream().map(this::fill).collect(Collectors.toList());
    }

    @Override
    public PayClient selectPayClient(Long clientId) {
        PayClientPo payClientPo = payClientMapper.selectById(clientId);
        return PayAppMapstruct.INSTANCE.toPayClient(payClientPo);
    }

    @Override
    public PayChannel selectPayChannel(Long channelId) {
        PayChannelPo payChannelPo = payChannelMapper.selectById(channelId);
        if (payChannelPo == null) {
            return null;
        }
        PayClientPo payClientPo = payClientMapper.selectOne(payChannelPo.getAppId(),
                payChannelPo.getChannelType());
        return PayAppMapstruct.INSTANCE.toPayChannel(payChannelPo, payClientPo);
    }

    @Override
    public PayChannel selectPayChannel(Long channelId, PayChannelEnum channelType) {
        PayChannelPo payChannelPo = payChannelMapper.selectOne(channelId, channelType.getCode());
        if (payChannelPo == null) {
            return null;
        }
        PayClientPo payClientPo = payClientMapper.selectOne(payChannelPo.getAppId(), payChannelPo.getChannelType());
        return PayAppMapstruct.INSTANCE.toPayChannel(payChannelPo, payClientPo);
    }

    @Override
    public void deleteApp(Long appId) {
        payAppMapper.deleteById(appId);
        List<PayChannelPo> payChannelPos = payChannelMapper.selectPayChannelList(appId);
        payChannelPos.forEach(payChannel -> this.deletePayChannel(payChannel.getChannelId()));
    }

    @Override
    public void deletePayChannel(Long channelId) {
        payChannelMapper.deleteById(channelId);
        payClientMapper.deleteByChannelId(channelId);
    }

    /**
     * 填充payChannel属性
     *
     * @param payChannelPo /
     * @return /
     */
    private PayApp fill(PayAppPo payChannelPo) {
        if (payChannelPo == null) {
            return null;
        }
        Long payChannelId = payChannelPo.getAppId();
        // 同时查询通道配置
        List<PayChannelPo> payChannelPos = payChannelMapper.selectPayChannelList(payChannelId);
        // 查询通道配置
        List<PayClientPo> payClientPos = payClientMapper.selectPayClientList(payChannelId);

        return PayAppMapstruct.INSTANCE.toPayApp(payChannelPo, payChannelPos, payClientPos);
    }

}

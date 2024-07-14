package pers.meteor.pay.domain.channel.service.impl;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.domain.channel.acl.RemotePayConfigAdapter;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.domain.channel.module.valueobject.SystemChannelConfig;
import pers.meteor.pay.domain.channel.repository.PayChannelRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PayChannelServiceImplTest {

    @Mock
    private PayChannelRepository payChannelRepository;

    @Mock
    private RemotePayConfigAdapter configAdapter;

    @InjectMocks
    private PayChannelServiceImpl payChannelService;

    private PayChannel payChannel;

    private SystemChannelConfig systemChannelConfig;

    private final EnumMap<PayChannelEnum, ChannelRate> defaultChannelRates = new EnumMap<>(PayChannelEnum.class);

    @BeforeEach
    void setUp() {
        // 初始化用于测试的支付渠道对象
        payChannel = new PayChannel();
        payChannel.setChannelId(1L);
        payChannel.setName("Test Channel");
        payChannel.setCode("TEST");

        systemChannelConfig = new SystemChannelConfig();
        systemChannelConfig.setUniqueCode(true);

        payChannelService.setConfigAdapter(configAdapter);

        ChannelRate channelRate = new ChannelRate();
        channelRate.setCostRate(new BigDecimal("0.0056"));
        channelRate.setCostFee(2);
        channelRate.setMaxRate(new BigDecimal("0.0060"));
        channelRate.setMinRate(new BigDecimal("0.0050"));
        channelRate.setMaxFee(5);
        channelRate.setMinFee(1);
        defaultChannelRates.put(PayChannelEnum.MOCK, channelRate);
    }

    @Test
    void addPayChannelShouldThrowServiceExceptionWhenChannelExists() {
        // 当渠道名称已存在时，模拟返回一个存在的支付渠道对象
        PayChannel expectPayChannel = new PayChannel();
        expectPayChannel.setName("Test Channel");
        expectPayChannel.setChannelId(2L);
        when(payChannelRepository.selectByName(payChannel.getName())).thenReturn(expectPayChannel);
        // 验证添加支付渠道时，如果渠道已存在，应抛出 ServiceException
        assertThrows(ServiceException.class, () -> payChannelService.addPayChannel(payChannel));
    }

    @Test
    void addPayChannelShouldSaveWhenChannelDoesNotExist() {
        // 当渠道名称不存在时，模拟返回 null
        when(payChannelRepository.selectByName(payChannel.getName())).thenReturn(null);
        when(configAdapter.getSystemChannelConfig()).thenReturn(systemChannelConfig);
        when(configAdapter.getDefaultRates()).thenReturn(defaultChannelRates);
        // 模拟保存成功
        when(payChannelRepository.save(any(PayChannel.class))).thenReturn(1L);
        // 验证添加支付渠道时，如果渠道不存在，应成功保存
        Long channelId = payChannelService.addPayChannel(payChannel);
        assertNotNull(channelId);
    }

    @Test
    void updatePayChannelShouldThrowServiceExceptionWhenChannelDoesNotExist() {
        // 当渠道不存在时，模拟返回 null
        when(payChannelRepository.selectById(payChannel.getChannelId())).thenReturn(null);
        // 验证更新支付渠道时，如果渠道不存在，应抛出 ServiceException
        assertThrows(ServiceException.class, () -> payChannelService.updatePayChannel(payChannel));
    }

    @Test
    void updatePayChannelShouldUpdateWhenChannelExists() {
        // 创建一个存在的支付渠道对象
        PayChannel existingChannel = new PayChannel();
        existingChannel.setChannelId(1L);
        // 当渠道存在时，模拟返回存在的支付渠道对象
        when(payChannelRepository.selectById(payChannel.getChannelId())).thenReturn(existingChannel);
        when(configAdapter.getSystemChannelConfig()).thenReturn(systemChannelConfig);
        when(configAdapter.getDefaultRates()).thenReturn(defaultChannelRates);
        // 模拟保存成功
        when(payChannelRepository.save(any(PayChannel.class))).thenReturn(1L);
        // 验证更新支付渠道时，如果渠道存在，应成功更新
        payChannelService.updatePayChannel(payChannel);
        verify(payChannelRepository, times(1)).save(any(PayChannel.class));
    }

    @Test
    void updateChannelConfigShouldThrowServiceExceptionWhenChannelDoesNotExist() {
        // 当渠道不存在时，模拟返回 null
        when(payChannelRepository.selectById(anyLong())).thenReturn(null);
        // 验证更新渠道配置时，如果渠道不存在，应抛出 ServiceException
        assertThrows(ServiceException.class, () -> payChannelService.updateChannelConfig(new ChannelConfig()));
    }

    @Test
    void updateChannelConfigShouldUpdateWhenChannelExists() {
        // 创建一个存在的支付渠道对象
        PayChannel existingChannel = new PayChannel();
        existingChannel.setChannelId(1L);
        // 当渠道存在时，模拟返回存在的支付渠道对象
        when(payChannelRepository.selectById(anyLong())).thenReturn(existingChannel);
        when(configAdapter.getSystemChannelConfig()).thenReturn(systemChannelConfig);
        when(configAdapter.getDefaultRates()).thenReturn(defaultChannelRates);
        // 模拟保存成功
        when(payChannelRepository.save(any(PayChannel.class))).thenReturn(1L);
        // 验证更新渠道配置时，如果渠道存在，应成功更新
        payChannelService.updateChannelConfig(new ChannelConfig());
        verify(payChannelRepository, times(1)).save(any(PayChannel.class));
    }

    @Test
    void updateChannelRateShouldThrowServiceExceptionWhenChannelDoesNotExist() {
        // 当渠道不存在时，模拟返回 null
        when(payChannelRepository.selectById(anyLong())).thenReturn(null);
        // 验证更新渠道费率时，如果渠道不存在，应抛出 ServiceException
        assertThrows(ServiceException.class, () -> payChannelService.updateChannelRate(1L, Collections.emptyList()));
    }

    @Test
    void updateChannelRateShouldUpdateWhenChannelExists() {
        // 创建一个存在的支付渠道对象
        // 当渠道存在时，模拟返回存在的支付渠道对象
        when(payChannelRepository.selectById(anyLong())).thenReturn(payChannel);
        when(configAdapter.getDefaultRates()).thenReturn(defaultChannelRates);
        // 模拟保存成功
        when(payChannelRepository.save(any(PayChannel.class))).thenReturn(1L);
        // 验证更新渠道费率时，如果渠道存在，应成功更新
        ChannelRate channelRate = new ChannelRate();
        channelRate.setCostRate(new BigDecimal("0.005"));
        channelRate.setCostFee(1);
        channelRate.setPayChannel(PayChannelEnum.MOCK);

        payChannelService.updateChannelRate(1L, Lists.list(channelRate));
        verify(payChannelRepository, times(1)).save(any(PayChannel.class));
    }

    @Test
    void enabledShouldThrowServiceExceptionWhenChannelDoesNotExist() {
        // 当渠道不存在时，模拟返回空Optional
        when(payChannelRepository.selectById(anyLong())).thenReturn(null);
        // 验证启用或禁用渠道时，如果渠道不存在，应抛出 ServiceException
        assertThrows(ServiceException.class, () -> payChannelService.enabled(1L, true));
    }

    @Test
    void enabledShouldUpdateWhenChannelExists() {
        // 创建一个存在的支付渠道对象
        PayChannel existingChannel = new PayChannel();
        existingChannel.setChannelId(1L);
        // 当渠道存在时，模拟返回存在的支付渠道对象
        when(payChannelRepository.selectById(anyLong())).thenReturn(existingChannel);
        // 模拟保存成功
        when(payChannelRepository.save(any(PayChannel.class))).thenReturn(1L);
        // 验证启用或禁用渠道时，如果渠道存在，应成功更新
        payChannelService.enabled(1L, true);
        verify(payChannelRepository, times(1)).save(any(PayChannel.class));
    }
}

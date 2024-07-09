package pers.meteor.pay.channel.application.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pers.meteor.pay.channel.domain.module.PayClientConfig;
import pers.meteor.pay.channel.domain.repository.PayChannelRepository;
import pers.meteor.pay.channel.domain.service.PayClient;
import pers.meteor.pay.channel.domain.service.PayClientFactory;

import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * @author meteor
 */
@Service
@RequiredArgsConstructor
public class PayClientCacheLoader extends CacheLoader<Long, PayClient> implements Serializable {
    private final PayChannelRepository payChannelRepository;
    private final PayClientFactory payClientFactory;

    @Override
    public PayClient load(@NonNull Long channelId) {
        PayClientConfig payClientConfig = payChannelRepository.selectPayClientConfig(channelId);
        if (payClientConfig != null) {
            payClientFactory.createOrUpdatePayClient(payClientConfig);
        }
        return payClientFactory.getPayClient(channelId);
    }

    public LoadingCache<Long, PayClient> buildAsyncLoadingCache() {
        return CacheBuilder.newBuilder()
                .refreshAfterWrite(Duration.ofSeconds(10L))
                .build(CacheLoader.asyncReloading(this, Executors.newCachedThreadPool()));
    }
}

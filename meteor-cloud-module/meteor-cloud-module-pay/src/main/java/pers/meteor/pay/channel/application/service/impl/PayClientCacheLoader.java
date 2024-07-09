package pers.meteor.pay.channel.application.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pers.meteor.pay.channel.domain.service.PayClient;

import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * @author meteor
 */
@Service
public class PayClientCacheLoader extends CacheLoader<Long, PayClient> implements Serializable {

    @Override
    public PayClient load(@NonNull Long channelId) throws Exception {
        return null;
    }

    public LoadingCache<Long, PayClient> buildAsyncLoadingCache() {
        return CacheBuilder.newBuilder()
                .refreshAfterWrite(Duration.ofSeconds(10L))
                .build(CacheLoader.asyncReloading(new PayClientCacheLoader(), Executors.newCachedThreadPool()));
    }
}

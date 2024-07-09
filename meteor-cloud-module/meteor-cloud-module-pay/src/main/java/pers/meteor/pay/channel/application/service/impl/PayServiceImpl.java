package pers.meteor.pay.channel.application.service.impl;

import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.pay.channel.application.service.PayService;
import pers.meteor.pay.channel.domain.service.PayClient;
/**
 * @author meteor
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    private final PayClientCacheLoader payClientCacheLoader;

    @Getter
    private final LoadingCache<Long, PayClient> clientCache = payClientCacheLoader.buildAsyncLoadingCache();

    public void createOrder() {

    }

    private PayClient getPayClient(Long channelId) {
        return clientCache.getUnchecked(channelId);
    }
}

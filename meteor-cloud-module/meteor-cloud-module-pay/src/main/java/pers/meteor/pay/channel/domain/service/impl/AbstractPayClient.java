package pers.meteor.pay.channel.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.channel.domain.module.PayClientConfig;
import pers.meteor.pay.channel.domain.service.PayClient;

/**
 * 支付模板
 *
 * @author meteor
 */
@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {
    /**
     * 通道id
     */
    private final Long channelId;
    /**
     * 通道配置
     */
    private Config config;

    public AbstractPayClient(Long channelId, Config config) {
        this.channelId = channelId;
        this.config = config;
    }

    /**
     * 初始化客户端
     */
    public final void initClient() {
        doInit();
        log.debug("[pay-client-{}] - 初始化完成", getChannelId());
    }

    /**
     * 自定义初始化客户端的逻辑
     */
    protected void doInit() {
        // empty
    }

    /**
     * 刷新配置
     *
     * @param config /
     */
    public void refreshConfig(Config config) {
        if (config.equals(this.config)) {
            log.debug("[pay-client-{}] - 配置未更新", getChannelId());
            return;
        }
        log.info("[pay-client-{}] - 配置发生更新，初始化配置", getChannelId());
        this.config = config;
        initClient();
    }

    @Override
    public Long getChannelId() {
        return this.channelId;
    }
}

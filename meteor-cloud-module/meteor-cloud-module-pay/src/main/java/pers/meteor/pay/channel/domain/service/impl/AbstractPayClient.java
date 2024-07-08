package pers.meteor.pay.channel.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.channel.domain.service.PayClient;
import pers.meteor.pay.channel.domain.service.PayClientConfig;

/**
 * 支付模板
 *
 * @author meteor
 */
@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {
}

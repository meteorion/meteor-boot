package pers.meteor.pay.domain.channel.service.impl;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.domain.channel.service.PayClient;
import pers.meteor.pay.domain.channel.service.PayClientConfig;

/**
 * 支付模板
 *
 * @author meteor
 */
@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {
}

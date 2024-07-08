package pers.meteor.pay.channel.application.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.pay.channel.application.service.PayService;
import pers.meteor.pay.channel.domain.service.PayClientFactory;

/**
 * @author meteor
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    private final PayClientFactory payClientFactory;


}

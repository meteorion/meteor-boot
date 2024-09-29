package pers.meteor.pay.adapter;

import org.springframework.stereotype.Service;
import pers.meteor.pay.domain.channel.acl.RemotePayConfigAdapter;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.domain.channel.module.valueobject.SystemChannelConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meteor
 */
@Service
public class RemoteConfigAdapterImpl implements RemotePayConfigAdapter {

    @Override
    public SystemChannelConfig getSystemChannelConfig() {
        return new SystemChannelConfig();
    }

    @Override
    public List<Rate> getDefaultRates() {
        return new ArrayList<>();
    }
}

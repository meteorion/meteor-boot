package pers.meteor.pay.application.channel.asselmber;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import pers.meteor.common.enums.SwitchStatusEnum;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.utils.StringUtils;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.enums.SignTypeEnum;

/**
 * @author meteor
 */
public interface BaseAssembler {
    default PayChannelEnum toPayChannelType(String code) {
        return PayChannelEnum.getByCode(code);
    }

    default SwitchStatusEnum toSwitchStatus(Integer code) {
        return SwitchStatusEnum.ofCode(code);
    }

    default JSONObject toJsonObject(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return new JSONObject();
        }
        if (!JSONUtil.isTypeJSON(jsonString)) {
            throw new ServiceException("请输入正确的json字符串");
        }
        return JSON.parseObject(jsonString);
    }

    default String toJsonString(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        return JSON.toJSONString(jsonObject);
    }

    default SignTypeEnum toSignType(String code) {
        return SignTypeEnum.getByCode(code);
    }
}

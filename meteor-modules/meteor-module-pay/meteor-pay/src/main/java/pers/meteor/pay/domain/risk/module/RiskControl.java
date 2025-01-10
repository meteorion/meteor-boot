package pers.meteor.pay.domain.risk.module;

import lombok.Data;
import pers.meteor.common.utils.StringUtils;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Data
public class RiskControl {
    private static final String SEPARATOR_COLON = ",";
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 服务id：beanName
     */
    private RiskControlTypeEnum riskControlType;
    /**
     * 启用状态
     */
    private Boolean open;
    /**
     * 参数组id
     */
    private List<String> paramGroupIds;
    /**
     * 风控参数
     */
    private List<RiskControlParam> riskControlParams;

    public void setParamGroupIds(String groupIds) {
        ArrayList<String> groupIdList = new ArrayList<>();
        if (StringUtils.isBlank(groupIds)) {
            this.paramGroupIds = groupIdList;
        }
        if (groupIds.contains(SEPARATOR_COLON)) {
            List<String> ids = Arrays.stream(groupIds.split(SEPARATOR_COLON)).collect(Collectors.toList());
            groupIdList.addAll(ids);
        } else {
            groupIdList.add(groupIds);
        }

        this.paramGroupIds = groupIdList;
    }
}

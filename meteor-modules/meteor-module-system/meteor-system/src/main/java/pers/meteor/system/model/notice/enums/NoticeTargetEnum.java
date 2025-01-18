package pers.meteor.system.model.notice.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import pers.meteor.common.pojo.BaseEnum;

import java.util.Collections;
import java.util.List;

/**
 * 通知目标类型枚举
 *
 * @author haoxr
 * @since 2022/10/14
 */
@Getter
@Schema(enumAsRef = true)
public enum NoticeTargetEnum implements BaseEnum {

    ALL(1, "全体"),
    SPECIFIED(2, "指定");


    private final Integer value;

    private final String label;

    NoticeTargetEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public List<Object> codes() {
        return Collections.emptyList();
    }
}

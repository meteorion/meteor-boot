package pers.meteor.system.mode.notice.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import pers.meteor.common.pojo.BaseEnum;

import java.util.Collections;
import java.util.List;

/**
 * 通告发布状态枚举
 *
 * @author haoxr
 * @since 2024/10/14
 */
@Getter
@Schema(enumAsRef = true)
public enum NoticePublishStatusEnum implements BaseEnum {

    UNPUBLISHED(0, "未发布"),
    PUBLISHED(1, "已发布"),
    REVOKED(-1, "已撤回");


    private final Integer value;

    private final String label;

    NoticePublishStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public List<Object> codes() {
        return Collections.emptyList();
    }
}

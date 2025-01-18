package pers.meteor.common.enums;

import lombok.Getter;
import pers.meteor.common.pojo.IntArrayValuable;

import java.util.Arrays;

/**
 * 用户状态
 *
 * @author meteor
 */
@Getter
public enum UserStatusEnum implements IntArrayValuable {
    ENABLE(0, "正常"),
    DISABLE(1, "停用"),
    DELETED(2, "删除");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserStatusEnum::getStatus).toArray();

    private final Integer status;
    private final String name;

    UserStatusEnum(Integer status, String name)
    {
        this.status = status;
        this.name = name;
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}

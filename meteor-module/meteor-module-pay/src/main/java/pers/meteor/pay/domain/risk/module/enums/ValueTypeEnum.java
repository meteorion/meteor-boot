package pers.meteor.pay.domain.risk.module.enums;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

/**
 * @author meteor
 */
@Getter
public enum ValueTypeEnum {
    /**
     * 值等于
     */
    VALUE_EQUALS(String.class) {
        @Override
        public boolean assertTrue(String source, Object target) {
            return ObjectUtils.nullSafeEquals(source, target);
        }
    },
    /**
     * 值包含
     */
    VALUE_NOT_CONTAINS(String.class) {
        @Override
        public boolean assertTrue(String source, Object target) {
            if (source == null || target == null) {
                return false;
            }
            if (target instanceof String) {
                return !String.valueOf(target).contains(source);
            }
            return false;
        }
    },
    /**
     * 值区间
     */
    VALUE_LIMIT(String.class) {
        @Override
        public boolean assertTrue(String source, Object target) {
            if (source == null || target == null) {
                return false;
            }
            if (target instanceof Integer) {
                if (source.contains(separatorComma)) {
                    String[] values = source.split(separatorComma);
                    int left = Integer.parseInt(values[0]);
                    int right = Integer.parseInt(values[1]);
                    return left < (Integer) target && (Integer) target < right;
                }
                return Integer.parseInt(source) < (Integer) target;
            }
            return false;
        }
    },
    /**
     * 唯一性
     */
    VALUE_UNIQUE(Boolean.class),
    VALUE_VALID(Boolean.class),
    /**
     * 长度相等
     */
    LENGTH_EQUALS(Integer.class) {
        @Override
        public boolean assertTrue(String length, Object target) {
            if (length == null || target == null) {
                return false;
            }
            if (target instanceof String) {
                return Integer.parseInt(length) == target.toString().length();
            }
            return false;
        }
    },
    /**
     * 最大长度
     */
    LENGTH_MAX(Integer.class) {
        @Override
        public boolean assertTrue(String length, Object target) {
            if (length == null || target == null) {
                return false;
            }
            if (target instanceof String) {
                return Integer.parseInt(length) > target.toString().length();
            }
            return false;
        }
    },
    /**
     * 最小长度
     */
    LENGTH_MIN(Integer.class) {
        @Override
        public boolean assertTrue(String length, Object target) {
            if (length == null || target == null) {
                return false;
            }
            if (target instanceof String) {
                return Integer.parseInt(length) < target.toString().length();
            }
            return false;
        }
    },
    FOUR_ELEMENT(Boolean.class),
    WHITE_LIST(Boolean.class),
    REPORT_TIMES(Boolean.class),
    ENABLE_REPORT(Boolean.class),
    ;

    protected final String separatorComma = ",";
    @Getter
    private final Class<?> valueType;

    ValueTypeEnum(Class<?> valueType) {
        this.valueType = valueType;
    }

    public boolean assertTrue(String source, Object target) {
        return true;
    }
}

package com.meteor.common.rule.core;

import lombok.Data;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
public class RuleParam<T> {
    private String name;
    private T value;
    private boolean result;
}

package com.meteor.common.rule.api;

import java.util.HashMap;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface Rule {

    String getName();

    int getPriority();

    boolean isEnabled();

    String getDescription();

    boolean evaluate(HashMap<String, Object> facts);
}

package com.meteor.common.cypto.core.annotation;

import java.lang.annotation.*;

/**
 * @author meteor
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
}

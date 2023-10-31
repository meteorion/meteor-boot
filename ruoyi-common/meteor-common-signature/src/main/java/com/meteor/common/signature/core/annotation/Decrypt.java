package com.meteor.common.signature.core.annotation;

import java.lang.annotation.*;

/**
 * @author meteor
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {
}

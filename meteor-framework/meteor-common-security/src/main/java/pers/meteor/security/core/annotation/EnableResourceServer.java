package pers.meteor.security.core.annotation;

import org.springframework.context.annotation.Import;
import pers.meteor.security.config.ResourceServerAutoConfiguration;
import pers.meteor.security.config.ResourceServerConfiguration;

import java.lang.annotation.*;

/**
 * @author lengleng
 * <p>
 * 资源服务注解
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({ ResourceServerAutoConfiguration .class, ResourceServerConfiguration.class})
public @interface EnableResourceServer {

}

package pers.meteor.security.core.annotation;

import org.springframework.context.annotation.Import;
import pers.meteor.security.config.MeteorResourceServerAutoConfiguration;
import pers.meteor.security.config.MeteorResourceServerConfiguration;

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
@Import({ MeteorResourceServerAutoConfiguration.class, MeteorResourceServerConfiguration.class})
public @interface EnableResourceServer {

}

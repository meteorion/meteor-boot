package pres.meteor.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import pres.meteor.feign.core.MeteorFeignInnerRequestInterceptor;
import pres.meteor.feign.core.MeteorFeignRequestCloseInterceptor;
import pres.meteor.feign.sentinel.ext.MeteorSentinelFeign;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.MeteorFeignClientsRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * sentinel 配置
 *
 * @author lengleng
 * @date 2020-02-12
 */
@Configuration(proxyBeanMethods = false)
@Import(MeteorFeignClientsRegistrar.class)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class MeteorFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return MeteorSentinelFeign.builder();
	}

	/**
	 * add http connection close header
	 * @return
	 */
	@Bean
	public MeteorFeignRequestCloseInterceptor pigFeignRequestCloseInterceptor() {
		return new MeteorFeignRequestCloseInterceptor();
	}

	/**
	 * add inner request header
	 * @return PigFeignInnerRequestInterceptor
	 */
	@Bean
	public MeteorFeignInnerRequestInterceptor pigFeignInnerRequestInterceptor() {
		return new MeteorFeignInnerRequestInterceptor();
	}

}

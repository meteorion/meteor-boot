package pers.meteor.gateway.filter;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class TokenAuthenticationFilter implements GlobalFilter, Ordered {

    private final WebClient webClient;

    public TokenAuthenticationFilter(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.webClient = WebClient.builder().filter(lbFunction).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}

package com.ifeb2.gwservice.conf;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class LimiterConfiguration {

    @Bean
    @Primary
    public KeyResolver remoteHostLimiterKey() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest()
                                .getRemoteAddress())
                        .getAddress()
                        .getHostAddress()
        );
    }

    @Bean("userServiceLimiter")
    public RedisRateLimiter userServiceRateLimiter() {
        return new RedisRateLimiter(10, 20);
    }

    @Bean("authServiceRateLimiter")
    public RedisRateLimiter authServiceRateLimiter() {
        return new RedisRateLimiter(10, 30);
    }

    @Bean("defaultRateLimiter")
    @Primary
    public RedisRateLimiter defaultRateLimiter() {
        return new RedisRateLimiter(50, 100);
    }

}

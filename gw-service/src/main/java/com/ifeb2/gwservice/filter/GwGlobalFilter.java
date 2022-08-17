package com.ifeb2.gwservice.filter;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class GwGlobalFilter implements GlobalFilter, Ordered {

    private final static String AUTH_HEADER = HttpHeaders.AUTHORIZATION.toLowerCase(Locale.ROOT);

    private final static String SECRET_KEY = "secret_key";
    private final static String SECRET_KEY_VAL = "test";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders header = request.getHeaders();

        log.info("请求URI:{}", request.getURI());

        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header(SECRET_KEY, SECRET_KEY_VAL);

        if (StrUtil.isNotBlank(header.getFirst(AUTH_HEADER))) {
            mutate.header(AUTH_HEADER, header.getFirst(AUTH_HEADER));
        }

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

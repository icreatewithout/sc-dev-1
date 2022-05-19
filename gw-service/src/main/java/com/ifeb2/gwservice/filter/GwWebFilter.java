package com.ifeb2.gwservice.filter;

import cn.hutool.core.util.StrUtil;
import com.ifeb2.gwservice.handller.RequestException;
import com.ifeb2.gwservice.utils.RsaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 拦截来自SBA服务的 actuator 请求
 */
@Configuration
@RequiredArgsConstructor
public class GwWebFilter implements WebFilter {

    private final PathMatcher delegate = new AntPathMatcher();

    private final static String X_SBA_CLIENT_NAME = "X-SBA-CLIENT";
    private final static String X_SBA_CLIENT_VAL = "X-SBA-CLIENT-REQUEST-INFO";

    @Value("${rsa.prikey}")
    private String prikey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (delegate.match("/actuator/**", request.getPath().toString())) {

            String token = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String val = request.getHeaders().get(X_SBA_CLIENT_NAME).get(0);

            if (StrUtil.isBlank(token) || StrUtil.isBlank(val)) {
                throw new RequestException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString());
            }

            try {
                String text = RsaUtils.decryptByPrivateKey(prikey, val);
                if (!text.equals(X_SBA_CLIENT_VAL)) {
                    throw new RequestException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString());
                }
            } catch (Exception e) {
                throw new RequestException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString());
            }

        }
        return chain.filter(exchange);
    }

}

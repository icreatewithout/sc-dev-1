package com.ifeb2.adminservice.conf;

import cn.hutool.core.util.StrUtil;
import com.ifeb2.adminservice.utils.RsaUtils;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements WebMvcConfigurer {

    private final RedisTemplate<String, String> redisTemplate;
    private final AuthInfo authInfo;
    private final static String X_SBA_CLIENT_NAME = "X-SBA-CLIENT";
    private final static String X_SBA_CLIENT_VAL = "X-SBA-CLIENT-REQUEST-INFO";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpHeadersProvider provider() {
        return instance -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            String token = redisTemplate.opsForValue().get("sba_token");
            if (StrUtil.isBlank(token)) {
                token = authInfo.getToken();
                if (null != token) {
                    redisTemplate.opsForValue().set("sba_token", token);
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                }
            } else {
                httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
            try {
                httpHeaders.add(X_SBA_CLIENT_NAME, RsaUtils.encryptByPublicKey(authInfo.getPubkey(), X_SBA_CLIENT_VAL));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return httpHeaders;
        };
    }

    @Bean
    public InstanceExchangeFilterFunction auditLog() {
        return (instance, request, next) -> next.exchange(request).doOnSubscribe((s) -> {
            log.info("request url :{}", request.url());
            log.info("header token: {}", request.headers().get(HttpHeaders.AUTHORIZATION).get(0));
            log.info("request status :{}", instance.getStatusInfo().getStatus());
        }).doOnSuccess(response -> {
            log.info("response statusCode :{}", response.statusCode().value());
            //TODO 判断某个注册的服务返回401时，删除token缓存，表示重新获取token
            if (instance.getRegistration().getName().equals("user-service")
                    && response.statusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                redisTemplate.delete("sba_token");
            }
        });
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }


}

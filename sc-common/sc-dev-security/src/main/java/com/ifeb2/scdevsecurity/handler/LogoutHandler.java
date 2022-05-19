package com.ifeb2.scdevsecurity.handler;

import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevcore.jjwt.JJwtService;
import com.ifeb2.scdevcore.jjwt.properties.JJwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class LogoutHandler implements LogoutSuccessHandler {

    private final JJwtService tokenProvider;
    private final JJwtProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenProvider.getLoginUser(httpServletRequest);
        if (null != loginUser) {
            redisTemplate.delete(properties.getOnlineKey() + loginUser.getId().toString());
        }
    }
}

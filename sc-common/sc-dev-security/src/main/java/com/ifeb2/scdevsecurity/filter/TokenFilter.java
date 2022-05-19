package com.ifeb2.scdevsecurity.filter;

import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.utils.SecurityUtils;
import com.ifeb2.scdevcore.jjwt.JJwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

    private final JJwtService tokenProvider;

    public TokenFilter(JJwtService tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        LoginUser loginUser = tokenProvider.getLoginUser(httpServletRequest);

        // 对于 Token 为空的不需要去查 Redis
        if (null != loginUser && null == SecurityUtils.getAuthentication()) {
            tokenProvider.checkRenewal(loginUser.getId());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, tokenProvider.getToken(httpServletRequest), loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

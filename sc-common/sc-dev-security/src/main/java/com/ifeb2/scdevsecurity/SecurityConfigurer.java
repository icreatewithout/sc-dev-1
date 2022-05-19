package com.ifeb2.scdevsecurity;

import com.ifeb2.scdevbase.annotation.AuthIgnore;
import com.ifeb2.scdevbase.annotation.ParamIgnore;
import com.ifeb2.scdevcore.jjwt.JJwtService;
import com.ifeb2.scdevsecurity.filter.RequestFilter;
import com.ifeb2.scdevsecurity.filter.TokenFilter;
import com.ifeb2.scdevsecurity.handler.LogoutHandler;
import com.ifeb2.scdevsecurity.handler.RequestAccessDeniedHandler;
import com.ifeb2.scdevsecurity.handler.RequestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Value("${rsa.prikey}")
    private String prikey;

    private final CorsFilter corsFilter;
    private final RequestAuthenticationEntryPoint authenticationEntryPoint;
    private final RequestAccessDeniedHandler accessDeniedHandler;
    private final RedisTemplate<String, Object> redisTemplate;

    private final JJwtService jJwtService;
    private final LogoutHandler logoutHandler;

    private final Set<String> authIgnores = new HashSet<>();
    private final Set<String> paramIgnores = new HashSet<>();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RequestFilter requestFilter() {
        return new RequestFilter(paramIgnores, redisTemplate,prikey);
    }

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter(jJwtService);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        handlerMethod();
        httpSecurity
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .addFilterBefore(requestFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/swagger-resources/**", "/webjars/**", "/*/api-docs", "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(authIgnores.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutHandler)
                .and().addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    public void handlerMethod() {
        RequestMappingHandlerMapping mapping = getHandlerMapping();
        Map<RequestMappingInfo, HandlerMethod> mappingHandlerMethods = mapping.getHandlerMethods();
        mappingHandlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
            AuthIgnore annotation = handlerMethod.getMethodAnnotation(AuthIgnore.class);
            if (null != annotation) {
                assert requestMappingInfo.getPatternsCondition() != null;
                Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
                authIgnores.addAll(patterns);
            }
            ParamIgnore paramIgnore = handlerMethod.getMethodAnnotation(ParamIgnore.class);
            if (null != paramIgnore) {
                assert requestMappingInfo.getPatternsCondition() != null;
                Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
                paramIgnores.addAll(patterns);
            }
        });
        log.info("authIgnores:{}", authIgnores);
        log.info("paramIgnores:{}", paramIgnores);
    }

    @Bean
    @Qualifier(value = "requestMappingHandlerMapping")
    RequestMappingHandlerMapping getHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

}

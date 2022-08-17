package com.ifeb2.adminservice.conf;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.UUID;

@Slf4j
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final AdminServerProperties adminServer;

    private final SecurityProperties security;

    public SecurityConfigurer(AdminServerProperties adminServer, SecurityProperties security) {
        this.adminServer = adminServer;
        this.security = security;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

        http.authorizeRequests(
                (authorizeRequests) ->
                        authorizeRequests
                                .antMatchers(HttpMethod.OPTIONS).permitAll()
                                .antMatchers(this.adminServer.path("/assets/**")).permitAll()
                                .antMatchers(this.adminServer.path("/instances/**")).permitAll()
                                .antMatchers(this.adminServer.path("/logout")).permitAll()
                                .antMatchers(this.adminServer.path("/login")).permitAll().anyRequest().authenticated()
                ).formLogin(
                        (formLogin) -> formLogin
                                .loginPage(this.adminServer.path("/login"))
                                .successHandler(successHandler).and()
                ).logout((logout) ->
                        logout.logoutUrl(this.adminServer.path("/logout")))
                .httpBasic(Customizer.withDefaults())
                .csrf().disable()
                .cors().and().addFilter(corsFilter())
                .headers().frameOptions().disable().and()
                .rememberMe((rememberMe) -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));
    }

    // Required to provide UserDetailsService for "remember functionality"
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(security.getUser().getName())
                .password("{noop}" + security.getUser().getPassword()).roles("USER");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration());
        return new CorsFilter(source);
    }

    private CorsConfiguration configuration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        return configuration;
    }
}

package com.ifeb2.scdevcore.configuration;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareComponent")
public class AuditorAwareConfiguration {

    private final static String targetMethodName= "setStrategyName";

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod(targetMethodName);
        methodInvokingFactoryBean.setArguments(SecurityContextHolder.MODE_THREADLOCAL);
        return methodInvokingFactoryBean;
    }

}

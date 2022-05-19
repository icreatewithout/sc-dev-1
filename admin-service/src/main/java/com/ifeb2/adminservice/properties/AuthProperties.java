package com.ifeb2.adminservice.properties;

import com.ifeb2.adminservice.conf.AuthInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthProperties {

    @Bean
    @ConfigurationProperties(prefix = "auth", ignoreUnknownFields = true)
    public AuthInfo authDto() {
        return new AuthInfo();
    }


}

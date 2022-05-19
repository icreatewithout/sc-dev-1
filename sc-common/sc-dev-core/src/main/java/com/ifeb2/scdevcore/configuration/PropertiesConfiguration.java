package com.ifeb2.scdevcore.configuration;

import com.ifeb2.scdevcore.captcha.CaptchaProperties;
import com.ifeb2.scdevcore.jjwt.properties.JJwtProperties;
import com.ifeb2.scdevcore.jjwt.properties.RsaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "login", ignoreUnknownFields = true)
    public CaptchaProperties loginProperties() {
        return new CaptchaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = true)
    public JJwtProperties securityProperties() {
        return new JJwtProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "rsa", ignoreUnknownFields = true)
    public RsaProperties rsaProperties() {
        return new RsaProperties();
    }

}

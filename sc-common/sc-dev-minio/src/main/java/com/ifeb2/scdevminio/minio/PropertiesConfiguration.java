package com.ifeb2.scdevminio.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "minio", ignoreUnknownFields = true)
    public MinioProperties minioProperties() {
        return new MinioProperties();
    }

}

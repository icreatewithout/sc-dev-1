package com.ifeb2.scdevswagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String title = "SWAGGER接口API";
    private String description = "SWAGGER接口API";
    private String termsOfServiceUrl = "";
    private String name = "SWAGGER接口API作者";
    private String url = "";
    private String email = "";
    private String version = "1.0";
}

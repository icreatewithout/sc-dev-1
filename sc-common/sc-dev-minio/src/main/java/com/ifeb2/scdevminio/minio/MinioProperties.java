package com.ifeb2.scdevminio.minio;

import lombok.Data;

@Data
public class MinioProperties {
    private String bucket;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String http;
    private String down;
}

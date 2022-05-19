package com.ifeb2.scdevcore.jjwt.properties;

import lombok.Data;

@Data
public class JJwtProperties {

    private String header;

    private String bearer;

    private String base64Secret;

    private Long expired;

    private String onlineKey;

    private String codeKey;

    private Long detect;

    private Long renew;

}

package com.ifeb2.adminservice.conf;

import com.ifeb2.adminservice.response.Result;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Data
public class AuthInfo {

    private String url;
    private String username;
    private String password;
    private String pubkey;

    public String getToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map = new HashMap<>();
        map.put("username", this.username);
        map.put("password", this.password);
        ResponseEntity<Result> result = restTemplate.postForEntity(this.url, map, Result.class);
        if (result.getBody().getCode() == 200) {
            return result.getBody().getData().toString();
        }
        return null;
    }

}

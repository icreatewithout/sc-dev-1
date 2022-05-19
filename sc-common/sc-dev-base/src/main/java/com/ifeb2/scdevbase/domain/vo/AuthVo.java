package com.ifeb2.scdevbase.domain.vo;

import lombok.Data;

@Data
public class AuthVo {

    private String username;
    private String password;
    private String imgId;
    private String code;

    /**
     * 0 账密登录 1 手机验证码登录 2 其他
     */
    private String type = "0";

}

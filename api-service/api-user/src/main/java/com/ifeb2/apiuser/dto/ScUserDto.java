package com.ifeb2.apiuser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ScUserDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;

    private String userName;

    private String password;

    private String phone;

    private String email;

    private String status;

    private String nickName;

    private String avatarUrl;

    private String type;
}

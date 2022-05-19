package com.ifeb2.scdevbase.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String userName;

    private String phone;

    private String email;

    private String nickName;

    private String avatarUrl;

    private String password;

    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;

    private String roleIds;

    private String type;

    private Boolean isAccountNonExpired = true;
    private Boolean isAccountNonLocked = true;
    private Boolean isCredentialsNonExpired = true;
    private Boolean isEnabled = true;
}

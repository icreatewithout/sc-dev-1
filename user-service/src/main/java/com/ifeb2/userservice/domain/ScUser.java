package com.ifeb2.userservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifeb2.scdevbase.domain.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sc_user")
public class ScUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8496711421055905196L;

    @Column(name = "user_name", length = 20, nullable = true, unique = true)
    private String userName;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email", length = 36)
    private String email;

    /**
     * 1 正常 2 锁定
     */
    @Column(name = "status", length = 2, nullable = false)
    private String status;

    /**
     * 昵称
     */
    @Column(name = "nick_name", length = 50)
    private String nickName;

    /**
     * 头像地址
     */
    @Column(name = "avatar_url", length = 256)
    private String avatarUrl;

    /**
     * 用户类型 0 超级管理员 1 系统端用户 2 其他
     */
    @Column(name = "type", length = 2)
    private String type;

    /**
     * 用户部门
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "dept_id", length = 22)
    private Long deptId;
}

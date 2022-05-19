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
@Table(name = "sc_user_role")
public class ScUserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2952392756289607027L;

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "user_id", length = 20)
    private Long userId;

    /**
     * 角色 id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "role_id", length = 20)
    private Long roleId;

}

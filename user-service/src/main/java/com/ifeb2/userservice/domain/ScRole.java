package com.ifeb2.userservice.domain;

import com.ifeb2.scdevbase.domain.BaseEntity;
import com.ifeb2.userservice.domain.vo.ScMenuVo;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sc_role")
public class ScRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 779739731916027232L;

    /**
     * 角色名称
     */
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "code", length = 45)
    private String code;

    /**
     * 状态 0 可以用 1 不可用
     */
    @Column(name = "status", length = 2)
    private String status;

    /**
     * 排序
     */
    @Column(name = "sort", length = 11)
    private Integer sort;

    @Transient
    private List<ScMenuVo> permissions = new ArrayList<>();
}

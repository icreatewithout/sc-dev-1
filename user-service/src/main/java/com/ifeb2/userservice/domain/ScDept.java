package com.ifeb2.userservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifeb2.scdevbase.domain.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sc_dept")
public class ScDept extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2742574668889488979L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "parent_id", length = 22)
    private Long parentId;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "code", length = 255)
    private String code;

    /**
     * 状态 1 可以用 2 不可用
     */
    @Column(name = "status", length = 2)
    private String status;

    /**
     * 排序
     */
    @Column(name = "sort", length = 11)
    private Integer sort;

    @Transient
    private List<ScDept> children;

    public ScDept(Long id){
        this.id = id;
    }
}

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
@Table(name = "sc_dept")
public class ScDict extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3466711385444848172L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "parent_id", length = 22)
    private Long parentId;

    @Column(name = "label", length = 45)
    private String label;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "val", length = 11)
    private Integer val;

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
}

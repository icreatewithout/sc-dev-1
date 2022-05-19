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
@Table(name = "sc_menu")
public class ScMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5238332648913188335L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "parent_id", length = 22)
    private Long parentId;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "href", length = 255)
    private String href;

    @Column(name = "path", length = 255)
    private String path;

    @Column(name = "permission", length = 45)
    private String permission;

    @Column(name = "icon", length = 45)
    private String icon;

    /**
     * 1 目录 2 菜单 3 按钮
     */
    @Column(name = "type", length = 2)
    private String type;

    /**
     * 1 显示 2 不显示
     */
    @Column(name = "is_show", length = 2)
    private String isShow;

    @Transient
    private List<ScMenu> children;

}

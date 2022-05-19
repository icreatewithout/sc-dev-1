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
@Table(name = "sc_role_menu")
public class ScRoleMenu extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6142959350029841366L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "role_id", length = 22)
    private Long roleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "menu_id", length = 22)
    private Long menuId;

}

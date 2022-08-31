package com.ifeb2.testservice.domain;

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
@Table(name = "sc_sds_test")
public class ShardingsphereTest extends BaseEntity implements Serializable {

    @Column(name = "test",length = 45)
    private String test;

    @Column(name = "level",length = 2)
    private Integer level;

}

package com.ifeb2.testservice.domain;

import com.ifeb2.scdevbase.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sc_seata_test")
public class ScSeataTest extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5532690121371063798L;

    private String name;
    private String val;

}

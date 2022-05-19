package com.ifeb2.scdevcore.component;

import com.ifeb2.scdevbase.utils.IDUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AutoIdentityGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object o) {
       return IDUtil.swId();
    }
}

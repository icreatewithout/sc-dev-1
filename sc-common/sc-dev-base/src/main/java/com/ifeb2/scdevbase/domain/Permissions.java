package com.ifeb2.scdevbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
public class Permissions implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 2896279863275506654L;

    private String permission;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return permission;
    }
}

package com.ifeb2.scdevbase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifeb2.scdevbase.domain.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 8935425640470848256L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String browser;

    private String address;

    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long loginTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long expireTime;

    private UserVo usersVo;

    private Set<Permissions> permissions;

    private Boolean isSuper = false;

    public LoginUser(Long id, UserVo usersVo, Set<Permissions> permissions, Boolean isSuper) {
        this.id = id;
        this.usersVo = usersVo;
        this.permissions = permissions;
        this.isSuper = isSuper;
    }

    @JsonIgnore
    public Long getUserId() {
        return usersVo.getId();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return usersVo.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return usersVo.getUserName();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return usersVo.getIsAccountNonExpired();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return usersVo.getIsAccountNonLocked();
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return usersVo.getIsCredentialsNonExpired();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return usersVo.getIsEnabled();
    }
}

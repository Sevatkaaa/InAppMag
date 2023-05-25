package com.sov.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleModel implements GrantedAuthority {
    INVESTOR,
    COMPANY,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

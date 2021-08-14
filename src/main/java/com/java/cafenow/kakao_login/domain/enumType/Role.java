package com.java.cafenow.kakao_login.domain.enumType;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN_NOT_PERMIT, ROLE_ADMIN_PERMIT, ROLE_CLIENT;

    public String getAuthority(){
        return name();
    }
}
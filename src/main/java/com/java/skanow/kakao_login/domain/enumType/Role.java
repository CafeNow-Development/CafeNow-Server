package com.java.skanow.kakao_login.domain.enumType;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT, ROLE_NOT_PERMIT;

    public String getAuthority(){
        return name();
    }
}
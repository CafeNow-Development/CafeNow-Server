package com.java.cafenow.util;

import com.java.cafenow.advice.exception.CAdminNotFoundException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.repository.AdminJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentAdminUtil {

    private final AdminJpaRepository adminJpaRepository;

    public String getCurrentUsername() {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else{
            username = principal.toString();
        }
        return username;
    }

    public Admin getCurrentAdmin() {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else{
            username = principal.toString();
        }
        return adminJpaRepository.findByEmail(username).orElseThrow(CAdminNotFoundException::new);
    }
}

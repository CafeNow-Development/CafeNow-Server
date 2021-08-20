package com.java.cafenow.security.authectication;

import com.java.cafenow.advice.exception.CAdminNotFoundException;
import com.java.cafenow.kakao_login.domain.enumType.Role;
import com.java.cafenow.kakao_login.repository.AdminJpaRepository;
import com.java.cafenow.staff.repository.StaffJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final AdminJpaRepository adminJpaRepository;
    private final StaffJpaRepository staffJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return adminJpaRepository.findByEmail(email).orElseThrow(CAdminNotFoundException::new);
    }

    public UserDetails CustomLoadUserByUsername(String email) {
        return staffJpaRepository.findByStaffEmail(email);
    }
}
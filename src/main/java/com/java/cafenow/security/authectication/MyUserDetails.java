package com.java.cafenow.security.authectication;

import com.java.cafenow.advice.exception.CAdminNotFoundException;
import com.java.cafenow.kakao_login.repository.AdminJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final AdminJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userJpaRepository.findByEmail(email).orElseThrow(CAdminNotFoundException::new);
    }
}
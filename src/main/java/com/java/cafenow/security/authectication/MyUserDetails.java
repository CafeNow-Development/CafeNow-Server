package com.java.cafenow.security.authectication;

import com.java.cafenow.advice.exception.CUserNotFoundException;
import com.java.cafenow.kakao_login.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userJpaRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);
    }
}
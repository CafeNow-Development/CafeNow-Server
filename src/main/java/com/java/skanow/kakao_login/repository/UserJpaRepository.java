package com.java.skanow.kakao_login.repository;

import com.java.skanow.kakao_login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndProvider(String email, String provider);
}
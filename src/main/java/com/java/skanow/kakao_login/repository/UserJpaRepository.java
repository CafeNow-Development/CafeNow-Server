package com.java.skanow.kakao_login.repository;

import com.java.skanow.kakao_login.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByEmailAndProvider(String email, String provider);
}
package com.java.skanow.kakao_login.repository;

import com.java.skanow.kakao_login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);
    Optional<User> findByUidAndProvider(String uid, String provider);
}
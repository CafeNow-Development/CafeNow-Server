package com.java.skanow.kakao_login.repository;

import com.java.skanow.kakao_login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {}
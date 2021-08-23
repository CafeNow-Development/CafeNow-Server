package com.java.cafenow.menu.repository;

import com.java.cafenow.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuJpaRepository extends JpaRepository<Menu, Long> { }

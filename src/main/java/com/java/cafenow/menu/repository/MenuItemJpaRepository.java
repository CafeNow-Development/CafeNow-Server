package com.java.cafenow.menu.repository;

import com.java.cafenow.menu.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemJpaRepository extends JpaRepository<MenuItem, Long> { }

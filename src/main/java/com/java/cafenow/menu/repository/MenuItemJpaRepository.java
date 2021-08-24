package com.java.cafenow.menu.repository;

import com.java.cafenow.menu.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItem, Long> { }

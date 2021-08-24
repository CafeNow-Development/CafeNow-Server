package com.java.cafenow.menu.repository;

import com.java.cafenow.menu.domain.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOptionJpaRepository extends JpaRepository<ItemOption, Long> { }

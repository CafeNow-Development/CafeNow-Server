package com.java.cafenow.menu.repository;

import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
    Menu findByStore(Store store);
}

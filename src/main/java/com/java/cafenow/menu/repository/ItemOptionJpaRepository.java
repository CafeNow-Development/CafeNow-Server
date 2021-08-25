package com.java.cafenow.menu.repository;

import com.java.cafenow.menu.domain.ItemOption;
import com.java.cafenow.menu.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOptionJpaRepository extends JpaRepository<ItemOption, Long> {
    List<ItemOption> findAllByMenuItem(MenuItem menuItem);


    @Query("select i from ItemOption i join fetch i.menuItem where i.menuItem = :menuItem")
    List<ItemOption> searchMenuItem(MenuItem menuItem);
}

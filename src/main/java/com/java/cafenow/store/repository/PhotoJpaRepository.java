package com.java.cafenow.store.repository;

import com.java.cafenow.store.domain.Photo;
import com.java.cafenow.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByStore(Store store);
}

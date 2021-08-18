package com.java.cafenow.store.repository;

import com.java.cafenow.store.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoJpaRepository extends JpaRepository<Photo, Long> { }

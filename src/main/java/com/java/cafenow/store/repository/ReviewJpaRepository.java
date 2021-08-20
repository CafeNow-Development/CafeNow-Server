package com.java.cafenow.store.repository;

import com.java.cafenow.store.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> { }
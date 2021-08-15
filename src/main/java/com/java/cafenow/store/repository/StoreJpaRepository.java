package com.java.cafenow.store.repository;

import com.java.cafenow.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreJpaRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByIsApplicationApproval(boolean isApplicationApproval);
    Optional<Store> findByBusinessNumber(String businessNumber);
}

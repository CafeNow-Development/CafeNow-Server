package com.java.cafenow.staff.repository;

import com.java.cafenow.staff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffJpaRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByStaffEmail(String email);
}
package com.java.cafenow.store.repository;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreJpaRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByIsApplicationApproval(boolean isApplicationApproval);
    Optional<Store> findByBusinessNumber(String businessNumber);
    Store findByAdmin(Admin admin);

    @Query("select s from Store s where s.cafeName like %:keyWord% or s.address like %:keyWord% order by s.storeIdx desc")
    List<Store> searchKeyword(String keyWord);
}

package com.java.cafenow.store.domain;

import com.java.cafenow.kakao_login.domain.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeIdx;

    @Column(nullable = false, length = 100)
    private String businessNumber;

    @Column(nullable = false, length = 50)
    private String cafeName;

    @Column(nullable = false, length = 1000)
    private String address;

    @Column(nullable = false, length = 50)
    private String cafeNumber;

    @Column(nullable = false, length = 50)
    private String phoneNumber;  // 완료 알림을 받을 전화번호

    @Column(nullable = false)
    private Boolean isApplicationApproval; //신청 승인 여부

    @Column(length = 1000)
    private String cafeContent;

    @Column(nullable = false, length = 500)
    private String cafeWeekendHour;

    @Column(nullable = false, length = 500)
    private String cafeWeekdayHour;

    @Column
    private Boolean isBusiness;

    @ManyToOne(targetEntity = Admin.class, fetch = LAZY)
    @JoinColumn(name = "adminIdx")
    private Admin admin;
}

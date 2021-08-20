package com.java.cafenow.store.domain;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.domain.enumType.Role;
import com.java.cafenow.store.domain.enumType.Business;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;

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

    @Enumerated(STRING)
    private Business business;

    @ManyToOne(targetEntity = Admin.class, fetch = LAZY)
    @JoinColumn(name = "adminIdx")
    private Admin admin;

    @OneToMany(
            mappedBy = "store",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Photo> photos = new ArrayList<>();

    // Store에서 파일 처리 위함
    public void addPhoto(Photo photo) {
        this.photos.add(photo);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getStore() != this)
            // 파일 저장
            photo.setStore(this);
    }

    @OneToMany(
            mappedBy = "store",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Review> reviews = new ArrayList<>();

    // Store에서 파일 처리 위함
    public void addReview(Review review) {
        this.reviews.add(review);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(review.getStore() != this)
            // 파일 저장
            review.setStore(this);
    }


    @Builder
    public Store(SaveStoreReqDto saveStoreReqDto, Admin admin) {
        this.businessNumber = saveStoreReqDto.getBusinessNumber();
        this.cafeName = saveStoreReqDto.getCafeName();
        this.address = saveStoreReqDto.getAddress();
        this.cafeNumber = saveStoreReqDto.getCafeNumber();
        this.phoneNumber = saveStoreReqDto.getPhoneNumber();
        this.isApplicationApproval = false;
        this.cafeContent = saveStoreReqDto.getCafeContent();
        this.cafeWeekendHour = saveStoreReqDto.getCafeWeekendHour();
        this.cafeWeekdayHour = saveStoreReqDto.getCafeWeekdayHour();
        this.business = Business.ROLE_CLOSED;
        this.admin = admin;
    }

    public void updateIsApplicationApproval(){
        this.isApplicationApproval = true;
    }
}

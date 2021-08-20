package com.java.cafenow.store.domain;

import com.java.cafenow.store.dto.review.SaveReViewReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewIdx;

    @Column(nullable = false)
    private int reviewStar;

    @Column(nullable = false, length = 10000)
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "storeIdx")
    private Store store;

    // Store 정보 저장
    public void setStore(Store store) {
        this.store = store;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!store.getReviews().contains(this))
            // 파일 추가
            store.getReviews().add(this);
    }
}

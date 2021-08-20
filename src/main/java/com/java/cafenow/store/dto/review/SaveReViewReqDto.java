package com.java.cafenow.store.dto.review;

import com.java.cafenow.store.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveReViewReqDto {

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String reviewContent;

    @Positive(message = "리뷰 점수는 0점 보다 커야합니다.")
    private int reviewStar;

    public Review saveReview() {
        return Review.builder()
                .reviewContent(this.reviewContent)
                .reviewStar(this.reviewStar)
                .build();
    }
}

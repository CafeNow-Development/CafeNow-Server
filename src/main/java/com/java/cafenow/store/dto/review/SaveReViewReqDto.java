package com.java.cafenow.store.dto.review;

import com.java.cafenow.store.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveReViewReqDto {

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String reviewContent;

    private int reviewStar;

    public Review saveReview() {
        return Review.builder()
                .reviewContent(this.reviewContent)
                .reviewStar(this.reviewStar)
                .build();
    }
}

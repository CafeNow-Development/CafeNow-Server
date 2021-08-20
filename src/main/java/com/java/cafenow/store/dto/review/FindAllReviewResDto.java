package com.java.cafenow.store.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllReviewResDto {

    private Long reviewIdx;
    private String reviewContent;
    private int reviewStar;

}

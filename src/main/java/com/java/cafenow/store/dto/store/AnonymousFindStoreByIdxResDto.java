package com.java.cafenow.store.dto.store;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.domain.enumType.Business;
import com.java.cafenow.store.dto.photo.FindByPhotoResDto;
import com.java.cafenow.store.dto.review.FindAllReviewResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnonymousFindStoreByIdxResDto {

    private Long storeIdx;
    private String adminName;
    private String cafeName;
    private String address;
    private String cafeNumber;
    private String phoneNumber;
    private String cafeContent;
    private String cafeWeekendHour;
    private String cafeWeekdayHour;
    private Business business;
    List<FindByPhotoResDto> findByPhotos;
    List<FindAllReviewResDto> findAllReviewRes;
    List<FindByStaff> findByStaffs;
}

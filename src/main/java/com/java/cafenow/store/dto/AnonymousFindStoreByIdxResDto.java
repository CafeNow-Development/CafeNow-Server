package com.java.cafenow.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnonymousFindStoreByIdxResDto {

    private Long storeIdx;
    private String cafeName;
    private String address;
    private String cafeNumber;
    private String phoneNumber;
    private String cafeContent;
    private String cafeWeekendHour;
    private String cafeWeekdayHour;
    private Boolean isBusiness;
    List<FindByPhotoResDto> findByPhotos;

}

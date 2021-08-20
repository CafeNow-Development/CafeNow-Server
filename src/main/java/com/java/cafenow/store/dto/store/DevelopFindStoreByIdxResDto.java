package com.java.cafenow.store.dto.store;

import com.java.cafenow.store.dto.photo.FindByPhotoResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevelopFindStoreByIdxResDto {

    private Long storeIdx;
    private String businessNumber;
    private String cafeName;
    private String address;
    private String cafeNumber;
    private String phoneNumber;
    private String IsApplicationApproval;
    private String cafeContent;
    private String cafeWeekendHour;
    private String cafeWeekdayHour;
    List<FindByPhotoResDto> findByPhotos;

}

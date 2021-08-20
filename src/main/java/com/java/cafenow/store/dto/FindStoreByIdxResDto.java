package com.java.cafenow.store.dto;

import com.java.cafenow.store.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindStoreByIdxResDto {

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
    List<FindByPhoto> findByPhotos;

}

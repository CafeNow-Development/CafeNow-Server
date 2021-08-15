package com.java.cafenow.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

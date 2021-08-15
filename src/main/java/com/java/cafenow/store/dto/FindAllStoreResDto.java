package com.java.cafenow.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllStoreResDto {

    private String storeIdx;
    private String businessNumber;
    private String cafeName;
    private String address;
    private String cafeNumber;
    private boolean IsApplicationApproval;

}

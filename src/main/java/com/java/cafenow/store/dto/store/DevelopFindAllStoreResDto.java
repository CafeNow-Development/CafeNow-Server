package com.java.cafenow.store.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevelopFindAllStoreResDto {

    private String storeIdx;
    private String businessNumber;
    private String cafeName;
    private String address;
    private String cafeNumber;
    private boolean IsApplicationApproval;

}

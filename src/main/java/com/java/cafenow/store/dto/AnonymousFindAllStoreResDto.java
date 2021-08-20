package com.java.cafenow.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnonymousFindAllStoreResDto {

    private String storeIdx;
    private String cafeName;
    private String address;
    private String cafeNumber;
    private Boolean isBusiness;

}

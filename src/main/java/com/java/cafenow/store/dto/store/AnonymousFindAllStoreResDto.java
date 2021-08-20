package com.java.cafenow.store.dto.store;

import com.java.cafenow.store.domain.enumType.Business;
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
    private Business business;

}

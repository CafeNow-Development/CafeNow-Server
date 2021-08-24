package com.java.cafenow.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindItemOptionResDto {

    private String itemOptionName;
    private int itemOptionPrice;

}

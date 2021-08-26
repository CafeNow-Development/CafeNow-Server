package com.java.cafenow.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAllMenuResDto {

    private String menuName;
    private int menuPrice;
    private String menuDesc;

}

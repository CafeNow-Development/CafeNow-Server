package com.java.cafenow.menu.dto;

import com.java.cafenow.menu.domain.enumType.MenuItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindMenuItemResDto {

    private String menuItemTitle;
    private MenuItemType menuItemType;

    @Builder.Default
    private List<FindItemOptionResDto> findItemOptionResDtoList = new ArrayList<>();
}

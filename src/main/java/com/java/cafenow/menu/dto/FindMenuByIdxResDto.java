package com.java.cafenow.menu.dto;

import com.java.cafenow.menu.domain.Menu;
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
public class FindMenuByIdxResDto {

    private Long menuIdx;
    private String menuName;
    private String menuDesc;

    @Builder.Default
    private List<FindMenuItemResDto> findMenuItemResDtoList = new ArrayList<>();

    public void mapping(Menu menu) {
        this.menuIdx = menu.getMenuIdx();
        this.menuName = menu.getMenuName();
        this.menuDesc = menu.getMenuDesc();
    }
}

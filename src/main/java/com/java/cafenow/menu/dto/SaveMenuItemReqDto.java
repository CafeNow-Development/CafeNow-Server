package com.java.cafenow.menu.dto;

import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.menu.domain.MenuItem;
import com.java.cafenow.menu.domain.enumType.MenuItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveMenuItemReqDto {

    private String menuItemTitle;
    private MenuItemType menuItemType;

    public MenuItem saveMenuItem() {
        return MenuItem.builder()
                .menuItemTitle(this.menuItemTitle)
                .menuItemType(this.menuItemType)
                .build();
    }

    private List<SaveItemOptionReqDto> itemOptionReqDtoList;
}

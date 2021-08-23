package com.java.cafenow.menu.dto;

import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.menu.domain.MenuItem;
import com.java.cafenow.menu.domain.enumType.MenuItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaveMenuItemReqDto {

    @NotBlank(message = "메뉴 아이템 제목을 입력해주세요.")
    private String MenuItemTitle;

    @NotBlank(message = "타입을 입력해줏오ㅛ/")
    private MenuItemType menuItemType;

    public MenuItem saveMenuItem(Menu menu) {
        return MenuItem.builder()
                .menuItemTitle(this.MenuItemTitle)
                .menuItemType(this.menuItemType)
                .menu(menu)
                .build();
    }
}

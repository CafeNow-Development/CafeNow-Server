package com.java.cafenow.menu.dto;

import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveMenuReqDto {

    private String menuName;
    private String menuDesc;
    private int menuPrice;

    public Menu saveMenu(Store store) {
        return Menu.builder()
                .menuName(this.menuName)
                .menuDesc(this.menuDesc)
                .menuPrice(this.menuPrice)
                .isSoldOut(false)
                .isHidden(false)
                .store(store)
                .build();
    }

    private List<SaveMenuItemReqDto> menuItemReqDtoList;
}

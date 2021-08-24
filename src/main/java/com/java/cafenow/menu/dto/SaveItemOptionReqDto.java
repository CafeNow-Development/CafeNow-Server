package com.java.cafenow.menu.dto;

import com.java.cafenow.menu.domain.ItemOption;
import com.java.cafenow.menu.domain.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveItemOptionReqDto {

    private String itemOptionName;
    private int itemOptionPrice;

    public ItemOption saveItemOption(MenuItem menuItem) {
        return ItemOption.builder()
                .itemOptionName(this.itemOptionName)
                .itemOptionPrice(this.itemOptionPrice)
                .menuItem(menuItem)
                .build();
    }
}

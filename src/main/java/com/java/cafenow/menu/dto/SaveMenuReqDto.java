package com.java.cafenow.menu.dto;

import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.menu.domain.MenuItem;
import com.java.cafenow.menu.domain.enumType.MenuItemType;
import com.java.cafenow.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveMenuReqDto {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String menuName;

    @NotBlank(message = "메뉴 설명을 입력해주세요.")
    private String menuDesc;

    private MultipartFile menuImage;
    private List<SaveMenuItemReqDto> menuItemReqDtoList;

    public Menu saveMenu(Store store) {
        return Menu.builder()
                .menuName(this.menuName)
                .menuDesc(this.menuDesc)
                .menuImage(this.menuDesc)
                .store(store)
                .build();
    }
}

package com.java.cafenow.menu.service;

import com.java.cafenow.menu.dto.FindAllMenuResDto;
import com.java.cafenow.menu.dto.FindMenuByIdxResDto;
import com.java.cafenow.menu.dto.SaveMenuReqDto;

import java.util.List;

public interface MenuService {

    void saveMenu(SaveMenuReqDto saveMenuReqDto, Long storeIdx);

    FindMenuByIdxResDto findMenu(Long menuIdx);

    List<FindAllMenuResDto> findAllMenu(Long storeIdx);

    // 메뉴 품절
    void soldOutMenu(Long menuIdx, Boolean isSoldOut);

    // 메뉴 숨김
    void hiddenMenu(Long menuIdx, Boolean isHidden);

    // 메뉴 이름 수정
    void updateMenuName(Long menuIdx, String menuName);

    // 메뉴 가격 수정
    void updateMenuPrice(Long menuIdx, int menuPrice);
}

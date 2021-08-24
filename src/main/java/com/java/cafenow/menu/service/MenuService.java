package com.java.cafenow.menu.service;

import com.java.cafenow.menu.dto.FindMenuByIdxResDto;
import com.java.cafenow.menu.dto.SaveMenuReqDto;

public interface MenuService {

    void saveMenu(SaveMenuReqDto saveMenuReqDto, Long storeIdx);

    FindMenuByIdxResDto findMenu(Long menuIdx);
}

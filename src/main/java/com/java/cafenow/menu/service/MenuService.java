package com.java.cafenow.menu.service;

import com.java.cafenow.menu.dto.FindAllMenuResDto;
import com.java.cafenow.menu.dto.FindMenuByIdxResDto;
import com.java.cafenow.menu.dto.SaveMenuReqDto;

import java.util.List;

public interface MenuService {

    void saveMenu(SaveMenuReqDto saveMenuReqDto, Long storeIdx);

    FindMenuByIdxResDto findMenu(Long menuIdx);

    List<FindAllMenuResDto> findAllMenu(Long storeIdx);
}

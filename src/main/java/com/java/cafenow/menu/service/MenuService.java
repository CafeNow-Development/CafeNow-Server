package com.java.cafenow.menu.service;


import com.java.cafenow.menu.dto.SaveMenuReqDto;

public interface MenuService {

    // 메뉴 저장 => Menu, MenuItem, ItemOption 까지 전체 저장
    void saveMenu(SaveMenuReqDto saveMenuReqDto, Long idx);

    // 메뉴 전체 조회

    // 메뉴 단일 조회

    // 메뉴 삭제

    // 메뉴 수정

}
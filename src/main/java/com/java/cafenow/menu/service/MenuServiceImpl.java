package com.java.cafenow.menu.service;

import com.java.cafenow.advice.exception.CStoreNotFoundException;
import com.java.cafenow.menu.domain.ItemOption;
import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.menu.domain.MenuItem;
import com.java.cafenow.menu.dto.*;
import com.java.cafenow.menu.repository.ItemOptionJpaRepository;
import com.java.cafenow.menu.repository.MenuItemJpaRepository;
import com.java.cafenow.menu.repository.MenuJpaRepository;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.repository.StoreJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuJpaRepository menuJpaRepository;
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final ItemOptionJpaRepository itemOptionJpaRepository;
    private final StoreJpaRepository storeJpaRepository;
    private final ModelMapper mapper;

    @Transactional
    @Override
    public void saveMenu(SaveMenuReqDto saveMenuReqDto, Long storeIdx) {
        Store store = storeJpaRepository.findById(storeIdx).orElseThrow(CStoreNotFoundException::new);
        Menu menu = menuJpaRepository.save(saveMenuReqDto.saveMenu(store));
        for (SaveMenuItemReqDto saveMenuItemReqDto : saveMenuReqDto.getMenuItemReqDtoList()) {
            MenuItem menuItem = menuItemJpaRepository.save(saveMenuItemReqDto.saveMenuItem());
            menu.addMenuItem(menuItem);
            for (SaveItemOptionReqDto saveItemOptionReqDto : saveMenuItemReqDto.getItemOptionReqDtoList()) {
                menuItem.addItemOption(itemOptionJpaRepository.save(saveItemOptionReqDto.saveItemOption(menuItem)));
            }
        }
    }

    @Override
    public FindMenuByIdxResDto findMenu(Long menuIdx) {
        FindMenuByIdxResDto findMenuByIdxResDto = new FindMenuByIdxResDto();
        List<FindItemOptionResDto> findItemOptionResDtoList = new ArrayList<>();

        Menu menu = menuJpaRepository.findById(menuIdx).orElseThrow(null);
        System.out.println("메뉴 확인 : " + menu.getMenuName());
        findMenuByIdxResDto.mapping(menu);

        List<MenuItem> menuItems = menu.getMenuItems();
        List<FindMenuItemResDto> findMenuItemResDtoList = menuItems.stream()
                .map(m -> mapper.map(m, FindMenuItemResDto.class))
                .collect(Collectors.toList());


        // Option 저장





        findMenuByIdxResDto.setFindMenuItemResDtoList(findMenuItemResDtoList);
        return findMenuByIdxResDto;
    }


    // 전체 조회


}

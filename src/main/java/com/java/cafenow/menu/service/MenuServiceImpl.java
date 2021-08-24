package com.java.cafenow.menu.service;

import com.java.cafenow.advice.exception.CStoreNotFoundException;
import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.menu.domain.MenuItem;
import com.java.cafenow.menu.dto.SaveItemOptionReqDto;
import com.java.cafenow.menu.dto.SaveMenuItemReqDto;
import com.java.cafenow.menu.dto.SaveMenuReqDto;
import com.java.cafenow.menu.repository.ItemOptionJpaRepository;
import com.java.cafenow.menu.repository.MenuItemJpaRepository;
import com.java.cafenow.menu.repository.MenuJpaRepository;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.repository.StoreJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuJpaRepository menuJpaRepository;
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final ItemOptionJpaRepository itemOptionJpaRepository;
    private final StoreJpaRepository storeJpaRepository;

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
}

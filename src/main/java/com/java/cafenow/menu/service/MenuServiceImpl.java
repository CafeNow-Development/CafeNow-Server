package com.java.cafenow.menu.service;

import com.java.cafenow.advice.exception.CStoreNotFoundException;
import com.java.cafenow.menu.domain.Menu;
import com.java.cafenow.menu.dto.SaveMenuItemReqDto;
import com.java.cafenow.menu.dto.SaveMenuReqDto;
import com.java.cafenow.menu.repository.ItemOptionJpaRepository;
import com.java.cafenow.menu.repository.MenuItemJpaRepository;
import com.java.cafenow.menu.repository.MenuJpaRepository;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final StoreJpaRepository storeJpaRepository;
    private final MenuJpaRepository menuJpaRepository;
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final ItemOptionJpaRepository itemOptionRepository;
    private final CurrentAdminUtil currentAdminUtil;


    @Transactional
    @Override
    public void saveMenu(SaveMenuReqDto saveMenuReqDto, Long idx) {
        log.info("스토어 검색");
        Store store = storeJpaRepository.findById(idx).orElseThrow(CStoreNotFoundException::new);
        log.info("메뉴 저장");
        Menu menu = menuJpaRepository.save(saveMenuReqDto.saveMenu(store));
        System.out.println("menu : " + menu);
        log.info("저장할 메뉴 아이템 리스트");
        List<SaveMenuItemReqDto> menuItemReqDtoList = saveMenuReqDto.getMenuItemReqDtoList();
        for (SaveMenuItemReqDto saveMenuItemReqDto : menuItemReqDtoList) {
            System.out.println("item : " + saveMenuItemReqDto);
            // 저장

        }
    }
}

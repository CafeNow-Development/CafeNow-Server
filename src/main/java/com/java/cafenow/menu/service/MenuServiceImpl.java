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
        Menu menu = getMenu(menuIdx);

        List<MenuItem> menuItems = menu.getMenuItems();
        List<FindMenuItemResDto> findMenuItemResDtoList = menuItems.stream()
                .map(m -> mapper.map(m, FindMenuItemResDto.class))
                .collect(Collectors.toList());

        getMenuItemAndOption(menuItems, findMenuItemResDtoList);

        findMenuByIdxResDto.mapping(menu);
        findMenuByIdxResDto.setFindMenuItemResDtoList(findMenuItemResDtoList);
        return findMenuByIdxResDto;
    }

    private void getMenuItemAndOption(List<MenuItem> menuItems, List<FindMenuItemResDto> findMenuItemResDtoList) {
        // 해당 아이템들에서 해당 옵션을 불러온다.
        menuItems.forEach(menuItem -> {
            List<FindItemOptionResDto> findItemOptionResDtoList = menuItem.getItemOptions().stream()
                    .map(m -> mapper.map(m, FindItemOptionResDto.class)).collect(Collectors.toList());

            findItemOptionResDtoList.forEach(itemOptionResDto -> {
                findMenuItemResDtoList.stream().filter(findMenuItemResDto -> findMenuItemResDto.getMenuItemTitle().equals(menuItem.getMenuItemTitle()))
                        .forEach(findMenuItemResDto -> findMenuItemResDto.getFindItemOptionResDtoList().add(itemOptionResDto));
            });
        });
    }

    @Override
    public List<FindAllMenuResDto> findAllMenu(Long storeIdx) {
        Store store = storeJpaRepository.findById(storeIdx).orElseThrow(CStoreNotFoundException::new);

        return menuJpaRepository.findAllByStore(store).stream()
                .map(m -> mapper.map(m, FindAllMenuResDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void soldOutMenu(Long menuIdx, Boolean isSoldOut) {
        Menu findMenuByIdx = getMenu(menuIdx);
        if(findMenuByIdx.getIsSoldOut().equals(isSoldOut)) {
            return;
        }
        findMenuByIdx.soldOut(isSoldOut);
    }

    @Transactional
    @Override
    public void hiddenMenu(Long menuIdx, Boolean isHidden) {
        Menu findMenuByIdx = getMenu(menuIdx);
        if(findMenuByIdx.getIsHidden().equals(isHidden)) {
            return;
        }
        findMenuByIdx.hidden(isHidden);
    }

    @Transactional
    @Override
    public void updateMenuName(Long menuIdx, String menuName) {
        Menu findMenuByIdx = getMenu(menuIdx);
        findMenuByIdx.updateMenuName(menuName);
    }

    @Transactional
    @Override
    public void updateMenuPrice(Long menuIdx, int menuPrice) {
        Menu findMenuByIdx = getMenu(menuIdx);
        findMenuByIdx.updateMenuPrice(menuPrice);
    }

    private Menu getMenu(Long menuIdx) {
        return menuJpaRepository.findById(menuIdx).orElseThrow(null);
    }
}

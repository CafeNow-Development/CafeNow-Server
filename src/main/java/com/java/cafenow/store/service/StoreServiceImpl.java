package com.java.cafenow.store.service;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.dto.FindAllStoreResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreJpaRepository storeJpaRepository;
    private final CurrentAdminUtil currentAdminUtil;
    private final ModelMapper mapper;

    @Transactional
    @Override
    public void saveStore(SaveStoreReqDto saveStoreReqDto) {
        Admin currentAdmin = currentAdminUtil.getCurrentAdmin();
        storeJpaRepository.save(saveStoreReqDto.saveStore(currentAdmin));
    }

    @Override
    public List<FindAllStoreResDto> findAllStore() {
        List<FindAllStoreResDto> findAllStoreResDtoList = storeJpaRepository.findAll()
                .stream().map(m -> mapper.map(m, FindAllStoreResDto.class))
                .collect(Collectors.toList());
        return findAllStoreResDtoList;
    }
}

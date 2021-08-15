package com.java.cafenow.store.service;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreJpaRepository storeJpaRepository;
    private final CurrentAdminUtil currentAdminUtil;

    @Transactional
    @Override
    public void saveStore(SaveStoreReqDto saveStoreReqDto) {
        Admin currentAdmin = currentAdminUtil.getCurrentAdmin();
        storeJpaRepository.save(saveStoreReqDto.saveStore(currentAdmin));
    }
}

package com.java.cafenow.store.service;

import com.java.cafenow.advice.exception.CDuplicationBusinessNumber;
import com.java.cafenow.advice.exception.CStoreNotFoundException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.dto.FindAllStoreResDto;
import com.java.cafenow.store.dto.FindStoreByIdxResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
import com.java.cafenow.util.message.sms.SMSService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreJpaRepository storeJpaRepository;
    private final CurrentAdminUtil currentAdminUtil;
    private final ModelMapper mapper;
    private final SMSService smsService;

    @Transactional
    @Override
    public void saveStore(SaveStoreReqDto saveStoreReqDto) {
        businessNumberCheck(saveStoreReqDto.getBusinessNumber());
        Admin currentAdmin = currentAdminUtil.getCurrentAdmin();
        storeJpaRepository.save(saveStoreReqDto.saveStore(currentAdmin));
    }

    public void businessNumberCheck(String businessNumber) {
        Optional<Store> findByBusinessNumber = storeJpaRepository.findByBusinessNumber(businessNumber);
        if(!findByBusinessNumber.isEmpty()){
            throw new CDuplicationBusinessNumber();
        }
    }

    @Override
    public List<FindAllStoreResDto> findAllStore() {
        List<FindAllStoreResDto> findAllStoreResDtoList = storeJpaRepository.findAllByIsApplicationApproval(false)
                .stream().map(m -> mapper.map(m, FindAllStoreResDto.class))
                .collect(Collectors.toList());
        return findAllStoreResDtoList;
    }

    @Override
    public FindStoreByIdxResDto findSingleStore(Long storeIdx) {
        FindStoreByIdxResDto findStoreByIdxResDto = storeJpaRepository.findById(storeIdx)
                .map(m -> mapper.map(m, FindStoreByIdxResDto.class))
                .orElseThrow(CStoreNotFoundException::new);
        return findStoreByIdxResDto;
    }

    @Transactional
    @Override
    public void updateApprovalStore(Long storeIdx) {
        Store findStoreByIdx = storeJpaRepository.findById(storeIdx).orElseThrow(CStoreNotFoundException::new);
        findStoreByIdx.updateIsApplicationApproval();
    }
}

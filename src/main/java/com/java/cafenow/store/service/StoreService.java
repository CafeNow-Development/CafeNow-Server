package com.java.cafenow.store.service;

import com.java.cafenow.store.dto.FindAllStoreResDto;
import com.java.cafenow.store.dto.FindStoreByIdxResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService {
    void saveStore(SaveStoreReqDto saveStoreReqDto, List<MultipartFile> files) throws Exception;
    List<FindAllStoreResDto> findAllStore();
    FindStoreByIdxResDto findSingleStore(Long storeIdx);
    void updateApprovalStore(Long storeIdx);
}

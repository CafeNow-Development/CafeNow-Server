package com.java.cafenow.store.service;

import com.java.cafenow.store.dto.DevelopFindAllStoreResDto;
import com.java.cafenow.store.dto.DevelopFindStoreByIdxResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService {
    void saveStore(SaveStoreReqDto saveStoreReqDto, List<MultipartFile> files) throws Exception;
    List<DevelopFindAllStoreResDto> DevelopfindAllStore();
    DevelopFindStoreByIdxResDto DevelopFindSingleStore(Long storeIdx);
    void updateApprovalStore(Long storeIdx);
}

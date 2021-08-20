package com.java.cafenow.store.service;

import com.java.cafenow.store.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService {
    void saveStore(SaveStoreReqDto saveStoreReqDto, List<MultipartFile> files) throws Exception;
    List<DevelopFindAllStoreResDto> DevelopFindAllStore();
    DevelopFindStoreByIdxResDto DevelopFindSingleStore(Long storeIdx);
    void updateApprovalStore(Long storeIdx);

    List<AnonymousFindAllStoreResDto> anonymousFindAllStore();
    AnonymousFindStoreByIdxResDto anonymousFindSingleStore(Long idx);
    void deleteStore(Long idx);
    List<AnonymousFindAllStoreResDto> findAllStoreByKeyword(String keyword);
}

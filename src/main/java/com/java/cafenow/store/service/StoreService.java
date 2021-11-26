package com.java.cafenow.store.service;

import com.java.cafenow.store.dto.review.FindAllReviewResDto;
import com.java.cafenow.store.dto.review.SaveReViewReqDto;
import com.java.cafenow.store.dto.store.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService {
    void saveStore(SaveStoreReqDto saveStoreReqDto, List<MultipartFile> files) throws Exception;
    List<DevelopFindAllStoreResDto> DevelopFindAllStore(String developToken) throws Exception;
    DevelopFindStoreByIdxResDto DevelopFindSingleStore(Long storeIdx, String developToken) throws Exception;
    void updateApprovalStore(Long storeIdx, String developToken) throws Exception;

    List<AnonymousFindAllStoreResDto> anonymousFindAllStore();
    AnonymousFindStoreByIdxResDto anonymousFindSingleStore(Long idx);
    void deleteStore(Long idx);
    List<AnonymousFindAllStoreResDto> findAllStoreByKeyword(String keyword);

    void saveReview(SaveReViewReqDto saveReViewReqDto, Long storeIdx);
}
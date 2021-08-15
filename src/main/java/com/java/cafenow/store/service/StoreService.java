package com.java.cafenow.store.service;

import com.java.cafenow.store.dto.FindAllStoreResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;

import java.util.List;

public interface StoreService {

    void saveStore(SaveStoreReqDto saveStoreReqDto);
    List<FindAllStoreResDto> findAllStore();

}

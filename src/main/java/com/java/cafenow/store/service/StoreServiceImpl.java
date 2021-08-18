package com.java.cafenow.store.service;

import com.java.cafenow.advice.exception.CAdminNotFoundException;
import com.java.cafenow.advice.exception.CDuplicationBusinessNumber;
import com.java.cafenow.advice.exception.CStoreNotFoundException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.repository.AdminJpaRepository;
import com.java.cafenow.store.domain.Photo;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.dto.FindAllStoreResDto;
import com.java.cafenow.store.dto.FindStoreByIdxResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import com.java.cafenow.store.repository.PhotoJpaRepository;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
import com.java.cafenow.util.message.sms.SMSService;
import com.java.cafenow.util.photo.FileUtil;
import com.java.cafenow.util.photo.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreJpaRepository storeJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final FileUtil fileUtil;
    private final S3Uploader s3Uploader;
    private final CurrentAdminUtil currentAdminUtil;
    private final ModelMapper mapper;
    //private final SMSService smsService;

    @Transactional
    @Override
    public void saveStore(SaveStoreReqDto saveStoreReqDto, List<MultipartFile> files) throws Exception {
        businessNumberCheck(saveStoreReqDto.getBusinessNumber());
        Admin currentAdmin = currentAdminUtil.getCurrentAdmin();
        Store store = new Store(saveStoreReqDto, currentAdmin);
//        List<Photo> photos = fileUtil.parseFileInfo(files);
        List<Photo> photos = s3Uploader.upload_image(files, "static");
        //파일이 존재할 때만 처리
        if(!photos.isEmpty()) {
            for (Photo photo : photos) {
                store.addPhoto(photoJpaRepository.save(photo));
            }
        }
        storeJpaRepository.save(store);
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

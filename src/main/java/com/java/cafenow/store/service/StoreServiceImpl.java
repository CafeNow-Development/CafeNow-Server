package com.java.cafenow.store.service;

import com.java.cafenow.advice.exception.CDuplicationBusinessNumber;
import com.java.cafenow.advice.exception.CStoreNotFoundException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.domain.Photo;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.dto.*;
import com.java.cafenow.store.repository.PhotoJpaRepository;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
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
        if(findByBusinessNumber.isPresent()){
            throw new CDuplicationBusinessNumber();
        }
    }

    @Override
    public List<DevelopFindAllStoreResDto> DevelopFindAllStore() {
        return storeJpaRepository.findAllByIsApplicationApproval(false)
                .stream().map(m -> mapper.map(m, DevelopFindAllStoreResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DevelopFindStoreByIdxResDto DevelopFindSingleStore(Long storeIdx) {
        DevelopFindStoreByIdxResDto findStoreByIdxResDto = storeJpaRepository.findById(storeIdx)
                .map(m -> mapper.map(m, DevelopFindStoreByIdxResDto.class))
                .orElseThrow(CStoreNotFoundException::new);

        List<FindByPhotoResDto> collect = findByStoreIdx(storeIdx).getPhotos()
                .stream().map(m -> mapper.map(m, FindByPhotoResDto.class))
                .collect(Collectors.toList());

        findStoreByIdxResDto.setFindByPhotos(collect);
        return findStoreByIdxResDto;
    }

    @Transactional
    @Override
    public void updateApprovalStore(Long storeIdx) {
        Store findStoreByIdx = storeJpaRepository.findById(storeIdx).orElseThrow(CStoreNotFoundException::new);
        findStoreByIdx.updateIsApplicationApproval();
    }

    @Override
    public List<AnonymousFindAllStoreResDto> anonymousFindAllStore() {
        return storeJpaRepository.findAll()
                .stream().map(m -> mapper.map(m, AnonymousFindAllStoreResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnonymousFindStoreByIdxResDto anonymousFindSingleStore(Long idx) {
        AnonymousFindStoreByIdxResDto anonymousFindStoreByIdxResDto = storeJpaRepository.findById(idx)
                .map(m -> mapper.map(m, AnonymousFindStoreByIdxResDto.class))
                .orElseThrow(CStoreNotFoundException::new);

        List<FindByPhotoResDto> collect =  findByStoreIdx(idx).getPhotos()
                .stream().map(m -> mapper.map(m, FindByPhotoResDto.class))
                .collect(Collectors.toList());

        anonymousFindStoreByIdxResDto.setFindByPhotos(collect);
        return anonymousFindStoreByIdxResDto;
    }

    private Store findByStoreIdx(Long idx){
        return storeJpaRepository.findById(idx).orElseThrow(CStoreNotFoundException::new);
    }
}

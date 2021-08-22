package com.java.cafenow.store.service;

import com.java.cafenow.advice.exception.CDuplicationBusinessNumber;
import com.java.cafenow.advice.exception.CStoreNotFoundException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.domain.Photo;
import com.java.cafenow.store.domain.Review;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.dto.photo.FindByPhotoResDto;
import com.java.cafenow.store.dto.review.FindAllReviewResDto;
import com.java.cafenow.store.dto.review.SaveReViewReqDto;
import com.java.cafenow.store.dto.store.*;
import com.java.cafenow.store.repository.PhotoJpaRepository;
import com.java.cafenow.store.repository.ReviewJpaRepository;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
import com.java.cafenow.util.photo.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreJpaRepository storeJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
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
        List<AnonymousFindAllStoreResDto> findAllStoreResDtos = storeJpaRepository.findAll()
                .stream().map(m -> mapper.map(m, AnonymousFindAllStoreResDto.class))
                .collect(Collectors.toList());
        return findAllStoreResDtos;
    }

    @Override
    public AnonymousFindStoreByIdxResDto anonymousFindSingleStore(Long idx) {

        log.info("Store Fetch Admin 조회");
        Optional<Store> store = storeJpaRepository.searchByIdx(idx);

        log.info("첫번째 DTO");
        AnonymousFindStoreByIdxResDto anonymousFindStoreByIdxResDto = store
                .map(m -> mapper.map(m, AnonymousFindStoreByIdxResDto.class))
                .orElseThrow(null);

        log.info("두번째 DTO");
        List<FindByPhotoResDto> findByPhotoResDtos =  store.get().getPhotos()
                .stream().map(m -> mapper.map(m, FindByPhotoResDto.class))
                .collect(Collectors.toList());

        log.info("세번째 DTO");
        List<FindAllReviewResDto> findAllReviewResDtos = store.get().getReviews()
                .stream().map(m -> mapper.map(m, FindAllReviewResDto.class))
                .collect(Collectors.toList());

        log.info("네번째 DTO");
        List<FindByStaff> findByStaffList = store.get().getAdmin().getStaffs()
                        .stream().map(m -> mapper.map(m, FindByStaff.class))
                        .collect(Collectors.toList());

        anonymousFindStoreByIdxResDto.setFindByPhotos(findByPhotoResDtos);
        anonymousFindStoreByIdxResDto.setFindAllReviewRes(findAllReviewResDtos);
        anonymousFindStoreByIdxResDto.setFindByStaffs(findByStaffList);
        return anonymousFindStoreByIdxResDto;
    }

    private Store findByStoreIdx(Long idx){
        return storeJpaRepository.findById(idx).orElseThrow(CStoreNotFoundException::new);
    }

    @Transactional
    @Override
    public void deleteStore(Long idx) {
        storeJpaRepository.deleteById(idx);
    }

    @Override
    public List<AnonymousFindAllStoreResDto> findAllStoreByKeyword(String keyword) {
        return storeJpaRepository.searchKeyword(keyword)
                .stream().map(m -> mapper.map(m, AnonymousFindAllStoreResDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveReview(SaveReViewReqDto saveReViewReqDto, Long storeIdx) {
        Store store = storeJpaRepository.findById(storeIdx).orElseThrow(CStoreNotFoundException::new);
        store.addReview(reviewJpaRepository.save(saveReViewReqDto.saveReview()));
    }
}

package com.java.cafenow.util.photo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.java.cafenow.store.domain.Photo;
import com.java.cafenow.store.dto.PhotoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final String S3_EQUIPMENT_IMG_SAVE_LOCATION = "static/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<Photo> upload_image(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        List<Photo> fileList = new ArrayList<>();
        List<File> uploadFile = new ArrayList<>();

        imgChk(multipartFiles); //이미지 체크 => 이미지가 비어 있는지, 확장가가 맞는지
        for (MultipartFile multipartFile : multipartFiles) {
            File file = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            uploadFile.add(file);
        }

        for (File file : uploadFile) {
            String upload = upload(file, dirName);

            // 파일 DTO 생성
            PhotoDto photoDto = PhotoDto.builder()
                    .filePath(upload)
                    .build();

            // 파일 DTO 이용하여 Photo 엔티티 생성
            Photo photo = new Photo(
                    photoDto.getFilePath()
            );

            fileList.add(photo);
        }
        return fileList;
    }



    private void imgChk(List<MultipartFile> multipartFiles){
        for (MultipartFile multipartFile : multipartFiles) {
            // 파일의 확장자 추출
            String originalFileExtension;
            String contentType = multipartFile.getContentType();

            // 확장자명이 존재하지 않을 경우 처리 x
            if(ObjectUtils.isEmpty(contentType)) {
                break;
            }
            else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                if(contentType.contains("image/jpeg"))
                    originalFileExtension = ".jpg";
                else if(contentType.contains("image/png"))
                    originalFileExtension = ".png";
                else  // 다른 확장자일 경우 처리 x
                    break;
            }
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(imgNameMake(file)); // 파일이름을 원래 파일이름 + 날짜 형식으로 저장
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public String imgNameMake(MultipartFile img){
        StringBuilder nameOfImg = new StringBuilder();

        String imgContentType = img.getContentType().split("/")[1];
        // 원래파일 이름 에서 확장자를 제거
        String originalName = img.getOriginalFilename().replace("." + imgContentType, "");
        nameOfImg.append(originalName);
        nameOfImg.append(new Date().getTime());
        // 파일에서 확장자를 다시 이어붙이기
        nameOfImg.append("." + imgContentType);

        return nameOfImg.toString();
    }

    private String upload(File uploadFile, String dirName) throws UnsupportedEncodingException {
        String fileName = dirName + "/" + uploadFile.getName();
        //file 저장후 UTF-8 로 변환후 저장
        String uploadImageUrl = putS3(uploadFile, fileName);  //파일을 S3에 저장후 s3 리소스에 접근하는 URL 받아오기
        uploadImageUrl = URLDecoder.decode(uploadImageUrl, "UTF-8");  //url 을 UTF-8 로 디코딩

        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public void deleteS3(String fileName){
        try {
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, fileName);
            //Delete
            this.amazonS3Client.deleteObject(deleteObjectRequest);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public String getLocationFileName(String fileLocation){
        //원본파일이름을 추출하기위해 슬래시("/") 기준으로 나눔, 원본 파일이름은 배열의 끝방에 있다.
        String[] splitFileLocationSplit = fileLocation.split("/");
        //배열의 끝방에 있는 원본파일이름을 추출하기위해 배열의 끝방 구하기
        int splitFileLocationSplitLastIdx = splitFileLocationSplit.length - 1;
        // 원본파일 이름
        String s3ImgOriginalImgName = splitFileLocationSplit[splitFileLocationSplitLastIdx];

        //저장할 위치와, 파일이름을 추가해서 리턴
        return S3_EQUIPMENT_IMG_SAVE_LOCATION + s3ImgOriginalImgName;
    }
}

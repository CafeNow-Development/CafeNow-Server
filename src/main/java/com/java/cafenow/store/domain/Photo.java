package com.java.cafenow.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileIdx;

    @Column(nullable = false)
    private String origFileName;  // 파일 원본명

    @Column(nullable = false)
    private String filePath;  // 파일 저장 경로

    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "storeIdx")
    private Store store;

    @Builder
    public Photo(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    // Store 정보 저장
    public void setStore(Store store) {
        this.store = store;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!store.getPhotos().contains(this))
            // 파일 추가
            store.getPhotos().add(this);
    }
}

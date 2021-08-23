package com.java.cafenow.menu.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itemOption")
public class ItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemOptionIdx;

    @Column(nullable = false, length = 10000)
    private String ItemOptionName;

    private int ItemOptionPrice;

    @ManyToOne(targetEntity = MenuItem.class, fetch = LAZY)
    @JoinColumn(name = "menuItemIdx")
    private MenuItem menuItem;

    // MenuItem 정보 저장
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!menuItem.getItemOptions().contains(this))
            // 파일 추가
            menuItem.getItemOptions().add(this);
    }
}
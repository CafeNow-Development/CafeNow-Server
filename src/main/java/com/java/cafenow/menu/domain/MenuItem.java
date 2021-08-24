package com.java.cafenow.menu.domain;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.menu.domain.enumType.MenuItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menuItem")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuItemIdx;

    @Column(nullable = false, length = 50000)
    private String menuItemTitle;

    @Enumerated(STRING)
    private MenuItemType menuItemType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menuIdx")
    private Menu menu;

    public void setMenu(Menu menu) {
        this.menu = menu;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!menu.getMenuItems().contains(this))
            // 파일 추가
            menu.getMenuItems().add(this);
    }
}

package com.java.cafenow.menu.domain;

import com.java.cafenow.menu.domain.enumType.MenuItemType;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menuItem")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuItemIdx;

    @Column(nullable = false, length = 50000)
    private String menuItemTitle;

    @Enumerated(STRING)
    private MenuItemType menuItemType;


    @ManyToOne(targetEntity = Menu.class, fetch = LAZY)
    @JoinColumn(name = "menuIdx")
    private Menu menu;

    // Menu 정보 저장
    public void setMenu(Menu menu) {
        this.menu = menu;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!menu.getMenuItems().contains(this))
            // 파일 추가
            menu.getMenuItems().add(this);
    }

    @OneToMany(
            mappedBy = "menuItem",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<ItemOption> itemOptions = new ArrayList<>();

    // Menu에서 파일 처리 위함
    public void addItemOption(ItemOption itemOption) {
        this.itemOptions.add(itemOption);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(itemOption.getMenuItem() != this)
            // 파일 저장
            itemOption.setMenuItem(this);
    }
}

package com.java.cafenow.menu.domain;

import com.java.cafenow.store.domain.Store;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu")
@ToString
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuIdx;

    @Column(nullable = false, length = 10000)
    private String menuName;

    @Column(length = 50000)
    private String menuDesc;

    @Column(length = 10000)
    private String menuImage;

    @ManyToOne(targetEntity = Store.class, fetch = LAZY)
    @JoinColumn(name = "storeIdx")
    @ToString.Exclude
    private Store store;

    @OneToMany(
            mappedBy = "menu",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<MenuItem> menuItems = new ArrayList<>();

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    // Menu에서 파일 처리 위함
    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(menuItem.getMenu() != this)
            // 파일 저장
            menuItem.setMenu(this);
    }
}

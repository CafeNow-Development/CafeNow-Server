package com.java.cafenow.staff.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.domain.enumType.Role;
import com.java.cafenow.store.domain.Store;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "staff")
public class Staff implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long staffIdx;

    @Column(nullable = false, unique = true, length = 30)
    private String staffEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String staffPassword;

    @Column(nullable = false, length = 100)
    private String staffName;

    @Column(nullable = false, length = 100)
    private String staffPhoneNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "adminIdx")
    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!admin.getStaffs().contains(this))
            // 파일 추가
            admin.getStaffs().add(this);
    }

    @Enumerated(STRING) @Column(name = "role")
    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "role_staff", joinColumns = @JoinColumn(name = "staff_id"))
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // List<Role> 형태를 Stream을 사용하여 roles 원소의 값을 String으로 바꿔주는 Enum.name()을 이용하여 List<String>형태로 변환(GrantedAuthority의 생성자는 String 타입을 받기 때문)
        List<String> rolesConvertString = this.roles.stream().map(Enum::name).collect(Collectors.toList());
        return rolesConvertString.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getRole() {
        return getAuthorities().iterator().next().toString();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.staffEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

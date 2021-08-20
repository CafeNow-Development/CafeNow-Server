package com.java.cafenow.staff.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.cafenow.kakao_login.domain.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffIdx;

    @Column(nullable = false, unique = true, length = 30)
    private String staffEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String staffPassword;

    @Column(nullable = false, length = 100)
    private String staffName;

    @Column(nullable = false, length = 100)
    private String staffPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "adminIdx")
    private Admin admin;
}

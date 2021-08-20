package com.java.cafenow.kakao_login.dto;

import com.java.cafenow.kakao_login.domain.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {

    private String email;
    private String name;
    private String role;
    private String provider;
    private String accessToken;

    public static LoginResDto mapping(Admin admin, String provider, String accessToken) {
        return LoginResDto.builder()
                .email(admin.getEmail())
                .name(admin.getName())
                .role(admin.getRole())
                .provider(provider)
                .accessToken(accessToken)
                .build();
    }
}

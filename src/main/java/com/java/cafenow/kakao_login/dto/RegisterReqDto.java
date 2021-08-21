package com.java.cafenow.kakao_login.dto;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.domain.enumType.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReqDto {

    @NotBlank(message = "Kakao에서 발급 받은 토큰을 입력해주세요.")
    private String access_token;

    public Admin saveAdmin(KakaoProfile profile, String provider) {
        return Admin.builder()
                .email(profile.getEmail())
                .provider(provider)
                .name(profile.getNickName())
                .roles(Collections.singletonList(Role.ROLE_ADMIN))
                .profile_image_url(profile.getProfile_image_url())
                .thumbnail_image_url(profile.getThumbnail_image_url())
                .is_email_valid(profile.getIs_email_valid())
                .is_email_verified(profile.getIs_email_verified())
                .build();
    }
}

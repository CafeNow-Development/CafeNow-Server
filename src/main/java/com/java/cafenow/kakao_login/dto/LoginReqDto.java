package com.java.cafenow.kakao_login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {

    @NotBlank(message = "Kakao에서 발급 받은 토큰을 입력해주세요.")
    private String accessToken;

}

package com.java.cafenow.kakao_login.controller;

import com.java.cafenow.advice.exception.CAdminExistException;
import com.java.cafenow.advice.exception.CAdminNotFoundException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.domain.enumType.Role;
import com.java.cafenow.kakao_login.dto.KakaoProfile;
import com.java.cafenow.kakao_login.repository.AdminJpaRepository;
import com.java.cafenow.kakao_login.service.KakaoService;
import com.java.cafenow.security.jwt.JwtTokenProvider;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.domain.SingleResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Kakao Login"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class KakaoLoginController {

    private final AdminJpaRepository userJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Admin user = userJpaRepository.findByEmailAndProvider(profile.getEmail(), provider).orElseThrow(CAdminNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getEmail(), user.getRoles()));
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<Admin> user = userJpaRepository.findByEmailAndProvider(profile.getEmail(), provider);
        if(user.isPresent())
            throw new CAdminExistException();

        userJpaRepository.save(Admin.builder()
                .email(profile.getEmail())
                .provider(provider)
                .name(profile.getNickName())
                .roles(Collections.singletonList(Role.ROLE_ADMIN_NOT_PERMIT))
                .profile_image_url(profile.getProfile_image_url())
                .thumbnail_image_url(profile.getThumbnail_image_url())
                .is_email_valid(profile.getIs_email_valid())
                .is_email_verified(profile.getIs_email_verified())
                .build());
        return responseService.getSuccessResult();
    }
}
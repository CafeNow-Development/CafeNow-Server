package com.java.cafenow.kakao_login.controller;

import com.java.cafenow.advice.exception.CAdminExistException;
import com.java.cafenow.advice.exception.CAdminNotFoundException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.dto.*;
import com.java.cafenow.kakao_login.repository.AdminJpaRepository;
import com.java.cafenow.kakao_login.service.KakaoService;
import com.java.cafenow.security.jwt.JwtTokenProvider;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.domain.SingleResult;
import com.java.cafenow.util.response.service.ResponseService;
import freemarker.template.utility.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    @PostMapping(value = "/login/{provider}")
    public SingleResult<LoginResDto> signinByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @Valid @RequestBody LoginReqDto loginReqDto) {
        KakaoProfile profile = kakaoService.getKakaoProfile(loginReqDto.getAccessToken());
        Admin admin = userJpaRepository.findByEmailAndProvider(profile.getEmail(), provider);

        if(admin == null) {
            Admin saveAdmin = userJpaRepository.save(loginReqDto.saveAdmin(profile, provider));
            LoginResDto login = LoginResDto.mapping(saveAdmin, provider, jwtTokenProvider.createTokenAdmin(saveAdmin));
            return responseService.getSingleResult(login);
        } else {
            LoginResDto login = LoginResDto.mapping(admin, provider, jwtTokenProvider.createTokenAdmin(admin));
            return responseService.getSingleResult(login);
        }
    }
}
package com.java.cafenow.kakao_login.service;

import com.google.gson.Gson;
import com.java.cafenow.advice.exception.CAdminNotFoundException;
import com.java.cafenow.advice.exception.CCommunicationException;
import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.dto.KakaoProfile;
import com.java.cafenow.kakao_login.dto.KakaoToken;
import com.java.cafenow.kakao_login.dto.LoginResDto;
import com.java.cafenow.kakao_login.dto.RetKakaoAuth;
import com.java.cafenow.kakao_login.repository.AdminJpaRepository;
import com.java.cafenow.security.jwt.JwtTokenProvider;
import com.java.cafenow.util.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KakaoService {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    @Value("${spring.social.kakao.client_secret}")
    private String clientSecret;

    @Value("${spring.social.kakao.grant_type}")
    private String grantType;

    public KakaoProfile getKakaoProfile(String accessToken) {
        HttpHeaders headers = getHttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // Request profile
            ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("spring.social.kakao.url.profile"), request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
                return kakaoProfile;
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public RetKakaoAuth getKakaoTokenInfo(String code) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = getHttpHeaders();

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirect);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("spring.social.kakao.url.token"), request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), RetKakaoAuth.class);
        }
        return null;
    }

    @NotNull
    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
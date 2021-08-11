package com.java.skanow.kakao_login.controller;

import com.java.skanow.kakao_login.domain.User;
import com.java.skanow.util.response.domain.SingleResult;
import com.java.skanow.util.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Test"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class TestController {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "테스트", notes = "테스트 home 출력")
    @GetMapping("/test")
    public String home() {
        return "home";
    }

}

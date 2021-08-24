package com.java.cafenow.menu.controller;

import com.java.cafenow.menu.dto.SaveMenuReqDto;
import com.java.cafenow.menu.service.MenuService;
import com.java.cafenow.staff.dto.StaffSaveReqDto;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"4. Menu"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MenuController {

    private final MenuService menuService;
    private final ResponseService responseService;

    @ApiOperation(value = "메뉴 등록", notes = "메뉴를 등록한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PostMapping("/cafe/menu/{storeIdx}")
    public CommonResult saveStore(@RequestBody SaveMenuReqDto saveMenuReqDto, @PathVariable Long storeIdx) throws Exception {
        menuService.saveMenu(saveMenuReqDto, storeIdx);
        return responseService.getSuccessResult();
    }

}

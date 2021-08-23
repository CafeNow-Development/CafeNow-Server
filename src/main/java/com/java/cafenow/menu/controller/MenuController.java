package com.java.cafenow.menu.controller;

import com.java.cafenow.menu.dto.SaveMenuReqDto;
import com.java.cafenow.menu.service.MenuService;
import com.java.cafenow.store.dto.store.SaveStoreReqDto;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. Menu"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MenuController {

    private final ResponseService responseService;
    private final MenuService menuService;

    @ApiOperation(value = "Admin 메뉴 등록", notes = "어드민이 메뉴를 등록한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PostMapping(value = "/admin/menu/{storeIdx}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResult saveStore(SaveMenuReqDto saveMenuReqDto, @PathVariable Long storeIdx) throws Exception {
        menuService.saveMenu(saveMenuReqDto, storeIdx);
        return responseService.getSuccessResult();
    }
}

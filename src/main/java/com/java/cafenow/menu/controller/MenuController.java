package com.java.cafenow.menu.controller;

import com.java.cafenow.menu.dto.*;
import com.java.cafenow.menu.service.MenuService;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.domain.ListResult;
import com.java.cafenow.util.response.domain.SingleResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CommonResult saveMenu(@RequestBody SaveMenuReqDto saveMenuReqDto, @PathVariable Long storeIdx) throws Exception {
        menuService.saveMenu(saveMenuReqDto, storeIdx);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "메뉴 전체 조회", notes = "메뉴를 단일 조회한다.")
    @ResponseBody
    @GetMapping("/cafe/menu/all/{storeIdx}")
    public ListResult<FindAllMenuResDto> AllMenu(@PathVariable Long storeIdx) throws Exception {
        List<FindAllMenuResDto> allMenu = menuService.findAllMenu(storeIdx);
        return responseService.getListResult(allMenu);
    }

    @ApiOperation(value = "메뉴 단일 조회", notes = "메뉴를 단일 조회한다.")
    @ResponseBody
    @GetMapping("/cafe/menu/get/{menuIdx}")
    public SingleResult<FindMenuByIdxResDto> getMenu(@PathVariable Long menuIdx) throws Exception {
        FindMenuByIdxResDto menu = menuService.findMenu(menuIdx);
        return responseService.getSingleResult(menu);
    }

    @ApiOperation(value = "메뉴 품절", notes = "메뉴를 품절 시킬 수 있다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PostMapping("/cafe/menu/sold-out/{menuIdx}")
    public CommonResult soldOutMenu(@PathVariable Long menuIdx, @RequestBody ChangeSoldOutReqDto isSoldOut) throws Exception {
        menuService.soldOutMenu(menuIdx, isSoldOut.getIsSoldOut());
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "메뉴 숨김", notes = "메뉴를 숨길 수 있다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PostMapping("/cafe/menu/hidden/{menuIdx}")
    public CommonResult HiddenMenu(@PathVariable Long menuIdx, @RequestBody ChangeHiddenReqDto isHidden) throws Exception {
        menuService.hiddenMenu(menuIdx, isHidden.getIsHidden());
        return responseService.getSuccessResult();
    }

}

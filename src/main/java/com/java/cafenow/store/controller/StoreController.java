package com.java.cafenow.store.controller;

import com.java.cafenow.store.dto.FindAllStoreResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import com.java.cafenow.store.service.StoreService;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.domain.ListResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2. Store"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class StoreController {

    private final StoreService storeService;
    private final ResponseService responseService;

    @ApiOperation(value = "매장 등록 신청", notes = "어드민이 매장을 등록 및 신청한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PostMapping("/store")
    public CommonResult saveStore(@RequestBody SaveStoreReqDto saveStoreReqDto) {
        storeService.saveStore(saveStoreReqDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "매장 등록 전체 신청 조회", notes = "개발자가 매장 등록 신청을 전체 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/store")
    public ListResult<FindAllStoreResDto> findAllStore() {
        List<FindAllStoreResDto> findAllStoreResDtoList = storeService.findAllStore();
        return responseService.getListResult(findAllStoreResDtoList);
    }
}

package com.java.cafenow.store.controller;

import com.java.cafenow.store.dto.FindAllStoreResDto;
import com.java.cafenow.store.dto.FindStoreByIdxResDto;
import com.java.cafenow.store.dto.SaveStoreReqDto;
import com.java.cafenow.store.service.StoreService;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.domain.ListResult;
import com.java.cafenow.util.response.domain.SingleResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Api(tags = {"2. Store"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class StoreController {

    private final StoreService storeService;
    private final ResponseService responseService;

    @ApiOperation(value = "Admin 매장 등록 신청", notes = "어드민이 매장을 등록 및 신청한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PostMapping(value = "/admin/store", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResult saveStore(SaveStoreReqDto saveStoreReqDto) throws Exception {
        storeService.saveStore(saveStoreReqDto, saveStoreReqDto.getFiles());
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "Develop 매장 등록 전체 신청 조회 (IsApplicationApproval == false)", notes = "개발자가 매장 등록 신청을 전체 조회한다.(IsApplicationApproval == False 인것만 조회)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/develop/store")
    public ListResult<FindAllStoreResDto> findAllStore() {
        List<FindAllStoreResDto> findAllStoreResDtoList = storeService.findAllStore();
        return responseService.getListResult(findAllStoreResDtoList);
    }

    @ApiOperation(value = "Develop 매장 등록 단일 신청 조회", notes = "개발자가 매장 등록 신청을 단일 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/develop/store/{storeidx}")
    public SingleResult<FindStoreByIdxResDto> findStoreByIdx(@PathVariable Long storeidx) {
        FindStoreByIdxResDto findSingleStore = storeService.findSingleStore(storeidx);
        return responseService.getSingleResult(findSingleStore);
    }

    @ApiOperation(value = "Develop 매장 등록 승인", notes = "개발자가 매장 등록을 승인한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PutMapping("/develop/approval-store/{storeidx}")
    public CommonResult ApprovalStore(@PathVariable Long storeidx) {
        storeService.updateApprovalStore(storeidx);
        return responseService.getSuccessResult();
    }

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResult create(@RequestPart(value="image") List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files) {
            System.out.println("file = " + file.getOriginalFilename());
        }
        return responseService.getSuccessResult();
    }
}

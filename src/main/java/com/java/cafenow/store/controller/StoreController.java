package com.java.cafenow.store.controller;

import com.java.cafenow.store.dto.*;
import com.java.cafenow.store.service.StoreService;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.domain.ListResult;
import com.java.cafenow.util.response.domain.SingleResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @ApiOperation(value = "Admin 매장 삭제", notes = "어드민이 매장을 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @DeleteMapping("/store/{storeidx}")
    public CommonResult deleteStoreByIdx(@PathVariable Long storeidx) {
        storeService.deleteStore(storeidx);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "Develop 매장 등록 전체 신청 조회 (IsApplicationApproval == false)", notes = "개발자가 매장 등록 신청을 전체 조회한다.(IsApplicationApproval == False 인것만 조회)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/develop/store")
    public ListResult<DevelopFindAllStoreResDto> findAllStore() {
        List<DevelopFindAllStoreResDto> findAllStoreResDtoList = storeService.DevelopFindAllStore();
        return responseService.getListResult(findAllStoreResDtoList);
    }

    @ApiOperation(value = "Develop 매장 등록 단일 신청 조회", notes = "개발자가 매장 등록 신청을 단일 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/develop/store/{storeidx}")
    public SingleResult<DevelopFindStoreByIdxResDto> findStoreByIdx(@PathVariable Long storeidx) {
        DevelopFindStoreByIdxResDto findSingleStore = storeService.DevelopFindSingleStore(storeidx);
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

    @ApiOperation(value = "Anonymous 매장 전체 조회", notes = "익명의 사용자가 매장을 전체 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/store")
    public ListResult<AnonymousFindAllStoreResDto> findAllStoreAnonymous() {
        List<AnonymousFindAllStoreResDto> anonymousFindAllStore = storeService.anonymousFindAllStore();
        return responseService.getListResult(anonymousFindAllStore);
    }

    @ApiOperation(value = "Anonymous 매장 단일 조회", notes = "익명의 사용자가 매장을 단일 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/store/{storeidx}")
    public SingleResult<AnonymousFindStoreByIdxResDto> findStoreByIdxAnonymous(@PathVariable Long storeidx) {
        AnonymousFindStoreByIdxResDto anonymousFindStoreByIdxResDto = storeService.anonymousFindSingleStore(storeidx);
        return responseService.getSingleResult(anonymousFindStoreByIdxResDto);
    }

    @ApiOperation(value = "매장 검색 조회 (카페 이름, 주소)", notes = "매장을 검색한다. (카페 이름, 주소 기준)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @GetMapping("/store/search")
    public ListResult<AnonymousFindAllStoreResDto> findAllByKeyWord(@RequestParam String keyword) {
        List<AnonymousFindAllStoreResDto> allStoreByKeyword = storeService.findAllStoreByKeyword(keyword);
        return responseService.getListResult(allStoreByKeyword);
    }
}

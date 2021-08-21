package com.java.cafenow.staff.controller;

import com.java.cafenow.staff.dto.StaffLoginReqDto;
import com.java.cafenow.staff.dto.StaffLoginResDto;
import com.java.cafenow.staff.dto.StaffSaveReqDto;
import com.java.cafenow.staff.service.StaffService;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.domain.SingleResult;
import com.java.cafenow.util.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. Staff"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class StaffController {

    private final StaffService staffService;
    private final ResponseService responseService;

    @ApiOperation(value = "Admin 스탭 등록", notes = "어드민이 스탭을 등록한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PostMapping("/admin/register/staff")
    public CommonResult saveStore(StaffSaveReqDto staffSaveReqDto) throws Exception {
        staffService.saveStaff(staffSaveReqDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "Staff 로그인", notes = "스탭이 로그인한다.")
    @ResponseBody
    @PostMapping("/login/staff")
    public SingleResult<StaffLoginResDto> saveStore(StaffLoginReqDto staffLoginReqDto) throws Exception {
        StaffLoginResDto staffLoginResDto = staffService.loginStaff(staffLoginReqDto);
        return responseService.getSingleResult(staffLoginResDto);
    }
}

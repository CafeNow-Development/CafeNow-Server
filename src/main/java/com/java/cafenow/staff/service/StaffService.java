package com.java.cafenow.staff.service;

import com.java.cafenow.staff.dto.StaffLoginReqDto;
import com.java.cafenow.staff.dto.StaffLoginResDto;
import com.java.cafenow.staff.dto.StaffSaveReqDto;

public interface StaffService {

    // 회원가입 (저장)
    void saveStaff(StaffSaveReqDto staffSaveReqDto) throws Exception;

    // 로그인
    StaffLoginResDto loginStaff(StaffLoginReqDto staffLoginReqDto) throws Exception;

    // 삭제
    void deleteStaff(Long idx);

}

package com.java.cafenow.staff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffLoginReqDto {

    @NotBlank(message = "스탭 이메일을 입력해주세요.")
    private String staffEmail;

    @NotBlank(message = "스탭 비밀번호를 입력해주세요.")
    private String staffPassword;

}

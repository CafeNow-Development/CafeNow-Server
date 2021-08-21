package com.java.cafenow.staff.dto;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.staff.domain.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffSaveReqDto {

    @NotBlank(message = "스탭 이메일을 입력해주세요.")
    private String staffEmail;

    @NotBlank(message = "스탭 비밀번호를 입력해주세요.")
    private String staffPassword;

    @NotBlank(message = "스탭 이름을 입력해주세요.")
    private String staffName;

    @NotBlank(message = "스탭 전화번호를 입력해주세요.")
    private String staffPhoneNumber;

    public Staff saveStaff(Admin admin) {
        return Staff.builder()
                .staffEmail(this.staffEmail)
                .staffPassword(this.staffPassword)
                .staffName(this.staffName)
                .staffPhoneNumber(this.staffPhoneNumber)
                .build();
    }
}

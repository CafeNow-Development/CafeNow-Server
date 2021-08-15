package com.java.cafenow.store.dto;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveStoreReqDto {

    // 카페 사장님이 매장을 등록할 때 사용하는 정보 => 요청
    @NotBlank(message = "사업자 번호를 입력해주세요.")
    private String businessNumber;

    @NotBlank(message = "카페 이름을 입력해주세요.")
    private String cafeName;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "카페 전화번호를 입력해주세요.")
    private String cafeNumber;

    @NotBlank(message = "개인 전화번호을 입력해주세요.")
    private String phoneNumber;

    private String cafeContent;

    @NotBlank(message = "평일 영업 시간을 입력해주세요.")
    private String cafeWeekdayHour;

    @NotBlank(message = "공휴일 영업 시간을 입력해주세요.")
    private String cafeWeekendHour;

    public Store saveStore(Admin admin) {
        return Store.builder()
                .businessNumber(this.businessNumber)
                .cafeName(this.cafeName)
                .address(this.address)
                .cafeNumber(this.cafeNumber)
                .phoneNumber(this.phoneNumber)
                .cafeContent(this.cafeContent)
                .cafeWeekdayHour(this.cafeWeekdayHour)
                .cafeWeekendHour(this.cafeWeekendHour)
                .admin(admin)
                .build();
    }
}

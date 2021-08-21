package com.java.cafenow.staff.service;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.security.jwt.JwtTokenProvider;
import com.java.cafenow.staff.domain.Staff;
import com.java.cafenow.staff.dto.StaffLoginReqDto;
import com.java.cafenow.staff.dto.StaffLoginResDto;
import com.java.cafenow.staff.dto.StaffSaveReqDto;
import com.java.cafenow.staff.repository.StaffJpaRepository;
import com.java.cafenow.store.domain.Store;
import com.java.cafenow.store.repository.StoreJpaRepository;
import com.java.cafenow.util.CurrentAdminUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffServiceImpl implements StaffService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StaffJpaRepository staffJpaRepository;
    private final CurrentAdminUtil currentAdminUtil;
    private final StoreJpaRepository storeJpaRepository;

    @Transactional
    @Override
    public void saveStaff(StaffSaveReqDto staffSaveReqDto) throws Exception {
        Admin currentAdmin = currentAdminUtil.getCurrentAdmin();
        Store findByAdmin = storeJpaRepository.findByAdmin(currentAdmin);
        checkAdmin(findByAdmin);

        Optional<Staff> staff = staffJpaRepository.findByStaffEmail(staffSaveReqDto.getStaffEmail());
        if(staff.isEmpty()) {
            String password = staffSaveReqDto.getStaffPassword();
            staffSaveReqDto.setStaffPassword(passwordEncoder.encode(password));
            staffJpaRepository.save(staffSaveReqDto.saveStaff(currentAdmin));
        } else {
            throw new Exception("스탭 이메일이 중복 되었습니다.");
        }
    }

    public void checkAdmin(Store store) throws Exception {
        if(store.getIsApplicationApproval() == false) {
            throw new Exception("아직 인증 되지 않는 매장 입니다. 인증을 기다려 주세요.");
        }
    }

    @Override
    public StaffLoginResDto loginStaff(StaffLoginReqDto staffLoginReqDto) throws Exception {
        String accessToken = null;
        String roles = null;
        StaffLoginResDto staffLoginResDto = null;
        Staff staff = staffJpaRepository.findByStaffEmail(staffLoginReqDto.getStaffEmail()).orElseThrow(null);
        if(staff != null) {
            boolean check = passwordEncoder.matches(staffLoginReqDto.getStaffPassword(), staff.getStaffPassword());
            if(!check) {
                throw new Exception("비밀번호가 다릅니다.");
            }
            accessToken = jwtTokenProvider.createTokenStaff(staff);
            roles = staff.getRole();
            staffLoginResDto = StaffLoginResDto.builder()
                    .staffEmail(staff.getStaffEmail())
                    .staffName(staff.getStaffName())
                    .accessToken(accessToken)
                    .role(roles)
                    .build();
        }
        return staffLoginResDto;
    }

    @Override
    public void deleteStaff(Long idx) {
        staffJpaRepository.deleteById(idx);
    }
}

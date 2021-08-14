package com.java.cafenow.kakao_login.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {
    private Long id;
    private kakao_account kakao_account;

    public Long getId() {
        return this.getId();
    }

    public String getEmail() {
        return kakao_account.getEmail();
    }

    public Boolean getIs_email_valid() {
        return kakao_account.getIs_email_valid();
    }

    public Boolean getIs_email_verified() {
        return kakao_account.getIs_email_verified();
    }

    public String getProfile_image_url() {
        return kakao_account.getProfile_image_url();
    }

    public String getThumbnail_image_url() {
        return kakao_account.getThumbnail_image_url();
    }

    public String getNickName() {
        return kakao_account.getNickName();
    }

    @Getter @Setter @ToString
    private static class kakao_account {
        private String email;
        private profile profile;
        private Boolean is_email_valid;
        private Boolean is_email_verified;

        public String getNickName() {
            return profile.getNickname();
        }

        public String getProfile_image_url() {
            return profile.getProfile_image_url();
        }

        public String getThumbnail_image_url() {
            return profile.getThumbnail_image_url();
        }

        @Getter @Setter @ToString
        private static class profile {
            private String nickname;
            private String profile_image_url;
            private String thumbnail_image_url;
        }
    }
}
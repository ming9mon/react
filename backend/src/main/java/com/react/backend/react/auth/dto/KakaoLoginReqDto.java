package com.react.backend.react.auth.dto;

import lombok.Data;

@Data
public class KakaoLoginReqDto {
    private String accessToken;
    private String refreshToken;
}

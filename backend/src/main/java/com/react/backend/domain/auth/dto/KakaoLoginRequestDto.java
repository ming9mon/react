package com.react.backend.api.auth.dto;

import lombok.Data;

@Data
public class KakaoLoginRequestDto {
    private String accessToken;
    private String refreshToken;
}

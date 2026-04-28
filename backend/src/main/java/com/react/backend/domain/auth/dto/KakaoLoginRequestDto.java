package com.react.backend.domain.auth.dto;

import com.react.backend.shared.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoLoginRequestDto extends BaseDto {
    private String accessToken;
    private String refreshToken;
}

package com.react.backend.domain.auth.dto;

import com.react.backend.shared.dto.TokenInfoDto;
import com.react.backend.shared.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private TokenInfoDto tokenInfo;
    private UserInfoDto userInfo;
    private Object userAuth; // TODO
}
package com.react.backend.domain.auth.dto;

import com.react.backend.shared.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverLoginRequestDto extends BaseDto {
    private String code;
}

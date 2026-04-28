package com.react.backend.domain.auth.dto;

import com.react.backend.shared.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto extends BaseDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String passWd;
}

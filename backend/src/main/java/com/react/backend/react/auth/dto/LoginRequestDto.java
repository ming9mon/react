package com.react.backend.react.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String passWd;
}

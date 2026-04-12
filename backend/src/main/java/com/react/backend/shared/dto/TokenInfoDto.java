package com.react.backend.shared.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfoDto {
    private String accessToken;
    private String refreshToken;
}
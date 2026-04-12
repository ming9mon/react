package com.react.backend.shared.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String userId;
    private String userNm;
    private String nickname;
}
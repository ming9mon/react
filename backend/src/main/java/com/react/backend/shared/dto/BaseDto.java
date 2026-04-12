package com.react.backend.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto {
    private String sessionId;   // 사용자 아이디
    private String accessIp;    // 접속 IP
}

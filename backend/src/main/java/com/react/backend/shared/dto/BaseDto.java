package com.react.backend.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto {
    private Long sessionUserSeq;    // 사용자 SEQ
    private String accessIp;        // 접속 IP
}

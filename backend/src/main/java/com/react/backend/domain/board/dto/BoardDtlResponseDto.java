package com.react.backend.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardDtlResponseDto {
    private String title;
    private String content;
}

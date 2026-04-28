package com.react.backend.api.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardListResponseDto {
    private Long seq;
    private String title;
}

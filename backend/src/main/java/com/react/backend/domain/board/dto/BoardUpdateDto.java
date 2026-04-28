package com.react.backend.api.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardUpdateDto {
    private Long seq;
    private String title;
    private String author;
    private String content;
}

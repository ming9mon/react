package com.react.backend.domain.board.dto;

import com.react.backend.shared.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDtlRequestDto extends BaseDto {
    private Long seq;
}

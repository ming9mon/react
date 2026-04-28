package com.react.backend.api.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class BoardListRequestDto {
    private int page = 0;
    private int pageSize = 10;
    private String sortBy;      // 정렬
    private Sort.Direction direction = Sort.Direction.DESC;

}

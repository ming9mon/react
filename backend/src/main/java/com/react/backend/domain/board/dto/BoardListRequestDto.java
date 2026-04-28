package com.react.backend.domain.board.dto;

import com.react.backend.shared.dto.PagingBaseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class BoardListRequestDto extends PagingBaseDto {
    private String sortBy;
    private Sort.Direction direction = Sort.Direction.DESC;
}

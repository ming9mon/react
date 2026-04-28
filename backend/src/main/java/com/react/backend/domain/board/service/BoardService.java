package com.react.backend.api.board.service;

import com.react.backend.api.board.dto.BoardDtlResponseDto;
import com.react.backend.api.board.dto.BoardListRequestDto;
import com.react.backend.api.board.dto.BoardListResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {

    Page<BoardListResponseDto> selectBoardList(BoardListRequestDto requestDto) throws Exception;

    BoardDtlResponseDto getDetail(Long seq);
}

package com.react.backend.react.board.service;

import com.react.backend.react.board.dto.BoardDtlResponseDto;
import com.react.backend.react.board.dto.BoardListRequestDto;
import com.react.backend.react.board.dto.BoardListResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {

    Page<BoardListResponseDto> selectBoardList(BoardListRequestDto requestDto) throws Exception;

    BoardDtlResponseDto getDetail(Long seq);
}

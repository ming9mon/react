package com.react.backend.domain.board.service;

import com.react.backend.domain.board.dto.BoardDtlResponseDto;
import com.react.backend.domain.board.dto.BoardListRequestDto;
import com.react.backend.domain.board.dto.BoardListResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {

    Page<BoardListResponseDto> selectBoardList(BoardListRequestDto requestDto) throws Exception;

    BoardDtlResponseDto getDetail(Long seq);
}

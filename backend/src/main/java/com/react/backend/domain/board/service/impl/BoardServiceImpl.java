package com.react.backend.domain.board.service.impl;

import com.react.backend.domain.board.dto.BoardDtlResponseDto;
import com.react.backend.domain.board.dto.BoardListRequestDto;
import com.react.backend.domain.board.dto.BoardListResponseDto;
import com.react.backend.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    public Page<BoardListResponseDto> selectBoardList(BoardListRequestDto requestDto) throws Exception {
        return null;
    }

    public BoardDtlResponseDto getDetail(Long seq) {
        return null;
    }
}
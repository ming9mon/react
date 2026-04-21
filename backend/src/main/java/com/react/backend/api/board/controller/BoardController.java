package com.react.backend.react.board.controller;

import com.react.backend.react.board.dto.BoardListRequestDto;
import com.react.backend.react.board.dto.BoardListResponseDto;
import com.react.backend.react.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value="/selectBoardList")
    public Page<BoardListResponseDto> selectBoardList(@RequestBody BoardListRequestDto boardListRequestDto) throws Exception {
        return boardService.selectBoardList(boardListRequestDto);
    }
}

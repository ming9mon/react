package com.react.backend.react.board.service.impl;

import com.react.backend.react.board.dto.BoardDtlResponseDto;
import com.react.backend.react.board.dto.BoardListRequestDto;
import com.react.backend.react.board.dto.BoardListResponseDto;
import com.react.backend.shared.repository.BoardRepository;
import com.react.backend.react.board.service.BoardService;
import com.react.backend.shared.entity.TBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardListResponseDto> selectBoardList(BoardListRequestDto requestDto) throws Exception {
        Sort sort = Sort.by(Sort.Direction.DESC, requestDto.getSortBy());
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), sort);

        Page<TBoard> boards = boardRepository.findAll(pageable);

        return boards.map(board -> BoardListResponseDto.builder()
                        .seq(board.getId())
                        .title(board.getTitle())
                        .build()
                );
    }

    public BoardDtlResponseDto getDetail(Long seq) {
        TBoard board = boardRepository
                .findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물"));

        return BoardDtlResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
package com.react.backend.domain.admin.multilingual.service;

import com.react.backend.domain.admin.multilingual.dto.DeleteMultilingualRequestDto;
import com.react.backend.domain.admin.multilingual.dto.DeleteMultilingualResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SaveMultilingualRequestDto;
import com.react.backend.domain.admin.multilingual.dto.SaveMultilingualResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualDetailResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualListResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualListRequestDto;
import com.react.backend.shared.dto.PagingResponseDto;
import com.react.backend.shared.entity.TLangBase;

import java.util.List;

public interface MultilingualService {

  /** 다국어 목록 조회 (페이징, 검색 조건) */
  PagingResponseDto<SearchMultilingualListResponseDto> getList(SearchMultilingualListRequestDto requestDto);

  /** 다국어 상세 조회 */
  SearchMultilingualDetailResponseDto getDetail(String multilingualKey);

  /** 다국어 등록/수정  */
  SaveMultilingualResponseDto save(SaveMultilingualRequestDto requestDto);

  /** 다국어 삭제 */
  DeleteMultilingualResponseDto delete(DeleteMultilingualRequestDto requestDto);
}

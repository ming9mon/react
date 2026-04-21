package com.react.backend.api.admin.multilingual.service;

import com.react.backend.api.admin.multilingual.dto.DeleteMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.DeleteMultilingualResponseDto;
import com.react.backend.api.admin.multilingual.dto.SaveMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.SaveMultilingualResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualDetailResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualRequestDto;
import com.react.backend.shared.entity.TLangBase;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MultilingualService {
  /** 언어 코드 목록 조회 */
  List<TLangBase> getLangList();

  /** 다국어 목록 조회 (페이징, 검색 조건) */
  Page<SearchMultilingualListResponseDto> getList(SearchMultilingualRequestDto requestDto);

  /** 다국어 상세 조회 */
  SearchMultilingualDetailResponseDto getDetail(String multilingualKey);

  /** 다국어 등록/수정 (key 존재 시 수정, 없으면 등록) */
  SaveMultilingualResponseDto saveOrUpdate(SaveMultilingualRequestDto requestDto);

  /** 다국어 삭제 */
  DeleteMultilingualResponseDto delete(DeleteMultilingualRequestDto requestDto);
}

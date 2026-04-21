package com.react.backend.api.admin.multilingual.service;

import com.react.backend.api.admin.multilingual.dto.SaveMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.SelectMultilingualDtlResponseDto;
import com.react.backend.api.admin.multilingual.dto.SelectMultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.SelectMultilingualRequestDto;
import com.react.backend.shared.entity.TLangBase;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MultilingualService {
  List<TLangBase> getLangList();
  Page<SelectMultilingualListResponseDto> getList(SelectMultilingualRequestDto requestDto);
  SelectMultilingualDtlResponseDto getDetail(String multilingualKey);
  void saveOrUpdate(SaveMultilingualRequestDto requestDto);
  void delete(String multilingualKey);
}

package com.react.backend.api.admin.multilingual.service;

import com.react.backend.api.admin.multilingual.dto.MultilingualDtlResponseDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualListRequestDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualSaveRequestDto;
import com.react.backend.shared.entity.TLangBase;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MultilingualService {
  Page<MultilingualListResponseDto> getList(MultilingualListRequestDto requestDto);
  MultilingualDtlResponseDto getDetail(String multilingualKey);
  void saveOrUpdate(MultilingualSaveRequestDto requestDto);
  void delete(String multilingualKey);
  List<TLangBase> getLangList();
}

package com.react.backend.react.multilingual.service;

import com.react.backend.react.multilingual.dto.MultilingualDtlResponseDto;
import com.react.backend.react.multilingual.dto.MultilingualListRequestDto;
import com.react.backend.react.multilingual.dto.MultilingualListResponseDto;
import com.react.backend.react.multilingual.dto.MultilingualSaveRequestDto;
import com.react.backend.shared.entity.TLangBase;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MultilingualService {
  Page<MultilingualListResponseDto> getList(MultilingualListRequestDto requestDto);
  MultilingualDtlResponseDto getDetail(String multilingualKey);
  void save(MultilingualSaveRequestDto requestDto);
  void update(String multilingualKey, MultilingualSaveRequestDto requestDto);
  void delete(String multilingualKey);
  List<TLangBase> getLangList();
}

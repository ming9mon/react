package com.react.backend.api.admin.multilingual.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultilingualRequestDto {
  private String mode; // LIST | LANG_LIST | DETAIL | SAVE | DELETE

  // 공통
  private String multilingualKey;

  // 목록 검색
  private String multilingualType;
  private String useYn;
  private int page = 0;
  private int pageSize = 10;

  // 등록/수정
  private String multilingualDesc;
  private List<MultilingualValueDto> values;
}

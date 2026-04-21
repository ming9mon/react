package com.react.backend.api.admin.multilingual.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMultilingualValueDto {
  private String langCd;          // 언어 코드 (예: ko_KR, en_US)
  private String langNm;          // 언어명 (예: 한국어, English)
  private String multilingualVal; // 번역 텍스트
}

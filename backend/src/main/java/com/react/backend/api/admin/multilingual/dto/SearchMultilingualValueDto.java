package com.react.backend.api.admin.multilingual.dto;

import lombok.Getter;
import lombok.Setter;

/** 다국어 상세 조회 응답 DTO - 언어별 번역 값 */
@Getter
@Setter
public class SearchMultilingualValueDto {
  /** 언어 코드 (예: ko_KR, en_US) */
  private String langCd;
  /** 언어명 (예: 한국어, English) */
  private String langNm;
  /** 번역 텍스트 */
  private String multilingualVal;
}

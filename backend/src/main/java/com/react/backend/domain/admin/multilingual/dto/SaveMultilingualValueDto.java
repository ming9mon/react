package com.react.backend.domain.admin.multilingual.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveMultilingualValueDto {
  private String langCd;          // 언어 코드 (예: ko_KR, en_US)
  private String multilingualVal; // 번역 텍스트
}

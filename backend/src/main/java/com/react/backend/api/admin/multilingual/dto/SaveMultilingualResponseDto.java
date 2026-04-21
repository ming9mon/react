package com.react.backend.api.admin.multilingual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 다국어 등록/수정 응답 DTO */
@Getter
@AllArgsConstructor
public class SaveMultilingualResponseDto {
  /** 저장된 다국어 키 */
  private final String multilingualKey;
  /** 처리 구분 (INSERT / UPDATE) */
  private final String processType;
}

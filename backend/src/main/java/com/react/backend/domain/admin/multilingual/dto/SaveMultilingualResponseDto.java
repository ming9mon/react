package com.react.backend.domain.admin.multilingual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveMultilingualResponseDto {
  private final String multilingualKey; // 저장된 다국어 키
  private final String processType;    // 처리 구분 (INSERT / UPDATE)
}

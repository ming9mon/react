package com.react.backend.domain.admin.multilingual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMultilingualResponseDto {
  private final String multilingualKey; // 다국어 키
}

package com.react.backend.api.admin.multilingual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMultilingualResponseDto {
  private final String multilingualKey; // 삭제된 다국어 키
}

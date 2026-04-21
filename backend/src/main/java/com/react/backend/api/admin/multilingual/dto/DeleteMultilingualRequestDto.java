package com.react.backend.api.admin.multilingual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMultilingualRequestDto {
  private final String multilingualKey; // 삭제할 다국어 키
}

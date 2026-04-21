package com.react.backend.api.admin.multilingual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 다국어 삭제 응답 DTO */
@Getter
@AllArgsConstructor
public class DeleteMultilingualResponseDto {
  /** 삭제된 다국어 키 */
  private final String multilingualKey;
}

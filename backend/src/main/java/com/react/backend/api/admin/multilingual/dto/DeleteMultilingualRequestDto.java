package com.react.backend.api.admin.multilingual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 다국어 삭제 요청 DTO */
@Getter
@AllArgsConstructor
public class DeleteMultilingualRequestDto {
  /** 삭제할 다국어 키 */
  private final String multilingualKey;
}

package com.react.backend.domain.admin.multilingual.dto;

import com.react.backend.shared.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMultilingualRequestDto extends BaseDto {
  private final String multilingualKey; // 다국어 키
}

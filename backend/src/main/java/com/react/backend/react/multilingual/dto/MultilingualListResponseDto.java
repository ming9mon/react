package com.react.backend.react.multilingual.dto;

import com.react.backend.shared.entity.TMultilingualBase;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MultilingualListResponseDto {
  private final String multilingualKey;
  private final String multilingualType;
  private final String useYn;
  private final String multilingualDesc;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public MultilingualListResponseDto(TMultilingualBase entity) {
    this.multilingualKey = entity.getMultilingualKey();
    this.multilingualType = entity.getMultilingualType();
    this.useYn = entity.getUseYn();
    this.multilingualDesc = entity.getMultilingualDesc();
    this.createdAt = entity.getCreatedAt();
    this.updatedAt = entity.getUpdatedAt();
  }
}

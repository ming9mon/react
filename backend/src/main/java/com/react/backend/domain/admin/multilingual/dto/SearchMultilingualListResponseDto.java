package com.react.backend.api.admin.multilingual.dto;

import com.react.backend.shared.entity.TMultilingualBase;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchMultilingualListResponseDto {
  private final String        multilingualKey;  // 다국어 키
  private final String        multilingualType; // 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR)
  private final String        useYn;            // 사용 여부 (Y / N)
  private final String        multilingualDesc; // 다국어 설명
  private final LocalDateTime createdAt;        // 생성 일시
  private final LocalDateTime updatedAt;        // 수정 일시

  public SearchMultilingualListResponseDto(TMultilingualBase entity) {
    this.multilingualKey  = entity.getMultilingualKey();
    this.multilingualType = entity.getMultilingualType();
    this.useYn            = entity.getUseYn();
    this.multilingualDesc = entity.getMultilingualDesc();
    this.createdAt        = entity.getCreatedAt();
    this.updatedAt        = entity.getUpdatedAt();
  }
}

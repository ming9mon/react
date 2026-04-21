package com.react.backend.api.admin.multilingual.dto;

import com.react.backend.shared.entity.TMultilingualBase;
import lombok.Getter;

import java.time.LocalDateTime;

/** 다국어 목록 조회 응답 DTO */
@Getter
public class SearchMultilingualListResponseDto {
  /** 다국어 키 */
  private final String multilingualKey;
  /** 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR) */
  private final String multilingualType;
  /** 사용 여부 (Y / N) */
  private final String useYn;
  /** 다국어 설명 */
  private final String multilingualDesc;
  /** 생성 일시 */
  private final LocalDateTime createdAt;
  /** 수정 일시 */
  private final LocalDateTime updatedAt;

  public SearchMultilingualListResponseDto(TMultilingualBase entity) {
    this.multilingualKey = entity.getMultilingualKey();
    this.multilingualType = entity.getMultilingualType();
    this.useYn = entity.getUseYn();
    this.multilingualDesc = entity.getMultilingualDesc();
    this.createdAt = entity.getCreatedAt();
    this.updatedAt = entity.getUpdatedAt();
  }
}

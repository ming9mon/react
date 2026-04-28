package com.react.backend.domain.admin.multilingual.dto;

import com.react.backend.shared.entity.TMultilingualBase;
import lombok.Getter;

@Getter
public class SearchMultilingualListResponseDto {
  private final String multilingualKey;  // 다국어 키
  private final String multilingualType; // 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR)
  private final String multilingualVal;  // 번역 텍스트 (해당 langCd 번역 없으면 null)
  private final String useYn;            // 사용 여부 (Y / N)

  public SearchMultilingualListResponseDto(TMultilingualBase base, String multilingualVal) {
    this.multilingualKey  = base.getMultilingualKey();
    this.multilingualType = base.getMultilingualType();
    this.multilingualVal  = multilingualVal;
    this.useYn            = base.getUseYn();
  }
}

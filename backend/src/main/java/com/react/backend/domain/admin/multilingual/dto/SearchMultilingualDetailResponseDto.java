package com.react.backend.api.admin.multilingual.dto;

import com.react.backend.shared.entity.TMultilingualBase;
import com.react.backend.shared.entity.TMultilingualValue;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SearchMultilingualDetailResponseDto {
  private final String                        multilingualKey;  // 다국어 키
  private final String                        multilingualType; // 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR)
  private final String                        useYn;            // 사용 여부 (Y / N)
  private final String                        multilingualDesc; // 다국어 설명
  private final List<SearchMultilingualValueDto> values;        // 언어별 번역 값 목록

  public SearchMultilingualDetailResponseDto(TMultilingualBase base, List<TMultilingualValue> valueList) {
    this.multilingualKey  = base.getMultilingualKey();
    this.multilingualType = base.getMultilingualType();
    this.useYn            = base.getUseYn();
    this.multilingualDesc = base.getMultilingualDesc();
    this.values = valueList.stream()
        .map(SearchMultilingualValueDto::new)
        .collect(Collectors.toList());
  }
}

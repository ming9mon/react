package com.react.backend.api.admin.multilingual.dto;

import com.react.backend.shared.entity.TMultilingualBase;
import com.react.backend.shared.entity.TMultilingualValue;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MultilingualDtlResponseDto {
  private final String multilingualKey;
  private final String multilingualType;
  private final String useYn;
  private final String multilingualDesc;
  private final List<MultilingualValueDto> values;

  public MultilingualDtlResponseDto(TMultilingualBase base, List<TMultilingualValue> valueList) {
    this.multilingualKey = base.getMultilingualKey();
    this.multilingualType = base.getMultilingualType();
    this.useYn = base.getUseYn();
    this.multilingualDesc = base.getMultilingualDesc();
    this.values = valueList.stream().map(v -> {
      MultilingualValueDto dto = new MultilingualValueDto();
      dto.setLangCd(v.getId().getLangCd());
      dto.setLangNm(v.getLangCd().getLangNm());
      dto.setMultilingualVal(v.getMultilingualVal());
      return dto;
    }).collect(Collectors.toList());
  }
}

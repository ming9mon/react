package com.react.backend.domain.admin.multilingual.dto;

import com.react.backend.shared.dto.PagingBaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMultilingualListRequestDto extends PagingBaseDto {
  @NotBlank
  private String langCd;           // 언어 코드 (필수)
  private String multilingualKey;  // 다국어 키
  private String multilingualType; // 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR)
  private String multilingualVal;  // 번역 텍스트
}

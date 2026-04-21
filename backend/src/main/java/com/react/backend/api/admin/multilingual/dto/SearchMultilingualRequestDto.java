package com.react.backend.api.admin.multilingual.dto;

import com.react.backend.shared.dto.PagingBaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMultilingualRequestDto extends PagingBaseDto {
  private String multilingualKey;  // 다국어 키 (부분 일치 검색)
  private String multilingualType; // 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR)
  private String useYn;            // 사용 여부 (Y / N)
}

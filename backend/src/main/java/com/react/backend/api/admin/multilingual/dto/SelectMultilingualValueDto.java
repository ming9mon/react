package com.react.backend.api.admin.multilingual.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectMultilingualValueDto {
  private String langCd;
  private String langNm;
  private String multilingualVal;
}

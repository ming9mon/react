package com.react.backend.api.admin.multilingual.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultilingualRequestDto {
  private String multilingualKey;
  private String multilingualType;
  private String useYn;
  private int page = 0;
  private int pageSize = 10;
}

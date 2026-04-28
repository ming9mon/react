package com.react.backend.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingBaseDto extends BaseDto {
  private int pageNo = 0;
  private int pageSize = 10;
}

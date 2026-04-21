package com.react.backend.api.admin.multilingual.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultilingualSaveRequestDto {
  @NotBlank
  @Size(max = 6)
  private String multilingualKey;

  @NotBlank
  @Size(max = 2)
  private String multilingualType;

  @NotBlank
  private String useYn;

  @Size(max = 500)
  private String multilingualDesc;

  @NotEmpty
  private List<MultilingualValueDto> values;
}

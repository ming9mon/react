package com.react.backend.api.admin.multilingual.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveMultilingualRequestDto {
  @NotBlank @Size(max = 6)
  private String multilingualKey;  // 다국어 키

  @NotBlank @Size(max = 2)
  private String multilingualType; // 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR)

  @NotBlank
  private String useYn;            // 사용 여부

  @Size(max = 500)
  private String multilingualDesc; // 다국어 설명

  @NotEmpty
  private List<SaveMultilingualValueDto> values; // 언어별 번역 값 목록
}

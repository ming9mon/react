package com.react.backend.api.admin.multilingual.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/** 다국어 등록/수정 요청 DTO (key 존재 시 수정, 없으면 등록) */
@Getter
@Setter
public class SaveMultilingualRequestDto {
  /** 다국어 키 (최대 6자) */
  @NotBlank
  @Size(max = 6)
  private String multilingualKey;

  /** 다국어 유형 (S: SCREEN / W: WORD / M: MESSAGE / E: ERROR) */
  @NotBlank
  @Size(max = 2)
  private String multilingualType;

  /** 사용 여부 (Y / N) */
  @NotBlank
  private String useYn;

  /** 다국어 설명 */
  @Size(max = 500)
  private String multilingualDesc;

  /** 언어별 번역 값 목록 */
  @NotEmpty
  private List<SaveMultilingualValueDto> values;
}

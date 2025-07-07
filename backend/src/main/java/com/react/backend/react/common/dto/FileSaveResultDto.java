package com.react.backend.react.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 파일 저장 결과 DTO
 */
@Getter
@Setter
@Builder
public class FileSaveResultDto {
  private String orgFileName;   // 원본 파일명
  private String savedFileName; // 저장된 파일명
  private String savedFilePath; // 저장된 경로
}

package com.react.backend.shared.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class InitResponseDto {
  private final List<ComboDto> langCdList;
  private final Map<String, String> multilingual;
}
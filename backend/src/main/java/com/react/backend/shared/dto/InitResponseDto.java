package com.react.backend.shared.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InitResponseDto {
  private final List<ComboDto> langCdList;
}
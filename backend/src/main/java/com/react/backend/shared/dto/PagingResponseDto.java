package com.react.backend.shared.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PagingResponseDto<T> {
  private final List<T> list;
  private final long totalElements;

  public PagingResponseDto(List<T> list, long totalElements) {
    this.list = list;
    this.totalElements = totalElements;
  }
}

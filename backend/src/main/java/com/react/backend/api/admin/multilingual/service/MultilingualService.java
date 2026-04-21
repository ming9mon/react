package com.react.backend.api.admin.multilingual.service;

import com.react.backend.api.admin.multilingual.dto.MultilingualRequestDto;
import com.react.backend.shared.dto.ResponseDto;

public interface MultilingualService {
  ResponseDto handle(MultilingualRequestDto requestDto);
}

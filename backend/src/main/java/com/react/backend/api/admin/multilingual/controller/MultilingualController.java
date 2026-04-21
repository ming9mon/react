package com.react.backend.api.admin.multilingual.controller;

import com.react.backend.api.admin.multilingual.dto.MultilingualRequestDto;
import com.react.backend.api.admin.multilingual.service.MultilingualService;
import com.react.backend.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/multilingual")
@RequiredArgsConstructor
public class MultilingualController {

  private final MultilingualService multilingualService;

  @PostMapping
  public ResponseDto handle(@RequestBody MultilingualRequestDto requestDto) {
    return multilingualService.handle(requestDto);
  }
}

package com.react.backend.domain.common.controller;

import com.react.backend.domain.common.service.InitService;
import com.react.backend.shared.dto.InitResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/init")
@RequiredArgsConstructor
public class InitController {

  private final InitService initService;

  @GetMapping
  public InitResponseDto getInit() {
    return initService.getInit();
  }
}
package com.react.backend.domain.common.service.impl;

import com.react.backend.domain.common.service.InitService;
import com.react.backend.shared.dto.ComboDto;
import com.react.backend.shared.dto.InitResponseDto;
import com.react.backend.shared.repository.LangBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InitServiceImpl implements InitService {

  private final LangBaseRepository langBaseRepository;

  @Override
  @Transactional(readOnly = true)
  public InitResponseDto getInit() {
    List<ComboDto> langCdList = langBaseRepository
        .findAll(Sort.by(Sort.Direction.ASC, "langCd"))
        .stream()
        .map(lang -> new ComboDto(lang.getLangCd(), lang.getLangNm()))
        .toList();

    return InitResponseDto.builder()
        .langCdList(langCdList)
        .build();
  }
}
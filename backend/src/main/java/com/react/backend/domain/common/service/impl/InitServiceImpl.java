package com.react.backend.domain.common.service.impl;

import com.react.backend.domain.common.service.InitService;
import com.react.backend.shared.dto.ComboDto;
import com.react.backend.shared.dto.InitResponseDto;
import com.react.backend.shared.repository.LangBaseRepository;
import com.react.backend.shared.repository.MultilingualValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InitServiceImpl implements InitService {

  private final LangBaseRepository langBaseRepository;
  private final MultilingualValueRepository multilingualValueRepository;

  @Override
  @Transactional(readOnly = true)
  public InitResponseDto getInit() {
    List<ComboDto> langCdList = langBaseRepository
        .findAll(Sort.by(Sort.Direction.ASC, "langCd"))
        .stream()
        .map(lang -> new ComboDto(lang.getLangCd(), lang.getLangNm()))
        .toList();

    Map<String, String> multilingual = multilingualValueRepository
        .findAllActiveByLangCd("ko_KR")
        .stream()
        .collect(Collectors.toMap(
            v -> v.getId().getMultilingualKey(),
            v -> v.getMultilingualVal()
        ));

    return InitResponseDto.builder()
        .langCdList(langCdList)
        .multilingual(multilingual)
        .build();
  }
}
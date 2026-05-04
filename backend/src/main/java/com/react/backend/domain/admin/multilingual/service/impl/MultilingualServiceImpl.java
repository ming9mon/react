package com.react.backend.domain.admin.multilingual.service.impl;

import com.react.backend.domain.admin.multilingual.dto.DeleteMultilingualRequestDto;
import com.react.backend.domain.admin.multilingual.dto.DeleteMultilingualResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SaveMultilingualRequestDto;
import com.react.backend.domain.admin.multilingual.dto.SaveMultilingualResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualDetailResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualListResponseDto;
import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualListRequestDto;
import com.react.backend.domain.admin.multilingual.repository.MultilingualRepositoryCustom;
import com.react.backend.domain.admin.multilingual.service.MultilingualService;
import com.react.backend.shared.dto.PagingResponseDto;
import com.react.backend.shared.entity.TLangBase;
import com.react.backend.shared.entity.TMultilingualBase;
import com.react.backend.shared.entity.TMultilingualValue;
import com.react.backend.shared.entity.TMultilingualValueId;
import com.react.backend.shared.repository.LangBaseRepository;
import com.react.backend.shared.repository.MultilingualBaseRepository;
import com.react.backend.shared.repository.MultilingualValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MultilingualServiceImpl implements MultilingualService {
  private final MultilingualBaseRepository multilingualBaseRepository;
  private final MultilingualValueRepository multilingualValueRepository;
  private final LangBaseRepository langBaseRepository;
  private final MultilingualRepositoryCustom multilingualRepositoryCustom;

  /** 다국어 목록 조회 (페이징, 검색 조건) */
  @Override
  @Transactional(readOnly = true)
  public PagingResponseDto<SearchMultilingualListResponseDto> getList(SearchMultilingualListRequestDto requestDto) {
    Page<SearchMultilingualListResponseDto> page = multilingualRepositoryCustom.searchMultilingualList(
        requestDto.getLangCd(),
        requestDto.getMultilingualKey(),
        requestDto.getMultilingualType(),
        requestDto.getMultilingualVal(),
        PageRequest.of(requestDto.getPageNo(), requestDto.getPageSize())
    );
    return new PagingResponseDto<>(page.getContent(), page.getTotalElements());
  }

  /** 다국어 상세 조회 */
  @Override
  @Transactional(readOnly = true)
  public SearchMultilingualDetailResponseDto getDetail(String multilingualKey) {
    TMultilingualBase base = multilingualBaseRepository.findById(multilingualKey)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + multilingualKey));
    List<TMultilingualValue> values = multilingualRepositoryCustom.findAllByMultilingualKey(multilingualKey);
    return new SearchMultilingualDetailResponseDto(base, values);
  }

  private static final Map<String, String> TYPE_SEQUENCE_MAP = Map.of(
      "S", "seq_multilingual_screen",
      "W", "seq_multilingual_word",
      "M", "seq_multilingual_message",
      "E", "seq_multilingual_error"
  );

  /** 다국어 등록/수정 (key 없으면 시퀀스로 생성 후 등록, key 있으면 수정) */
  @Override
  @Transactional
  public SaveMultilingualResponseDto save(SaveMultilingualRequestDto requestDto) {
    String key = requestDto.getMultilingualKey();
    boolean isNew = !StringUtils.hasText(key);

    if (isNew) {
      String type = requestDto.getMultilingualType();
      String sequenceName = TYPE_SEQUENCE_MAP.get(type);
      if (sequenceName == null) {
        throw new IllegalArgumentException("유효하지 않은 다국어 유형입니다: " + type);
      }
      long nextVal = multilingualRepositoryCustom.nextSequenceValue(sequenceName);
      key = type + String.format("%05d", nextVal);
    } else if (!multilingualBaseRepository.existsById(key)) {
      throw new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + key);
    }

    final String resolvedKey = key;

    TMultilingualBase base = isNew ? new TMultilingualBase()
        : multilingualBaseRepository.findById(resolvedKey)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + resolvedKey));

    base.setMultilingualKey(resolvedKey);
    base.setMultilingualType(requestDto.getMultilingualType());
    base.setUseYn(requestDto.getUseYn());
    base.setMultilingualDesc(requestDto.getMultilingualDesc());

    multilingualBaseRepository.save(base);

    requestDto.getValues().forEach(valueDto -> {
      TLangBase langBase = langBaseRepository.findById(valueDto.getLangCd())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 언어 코드입니다: " + valueDto.getLangCd()));

      TMultilingualValueId id = new TMultilingualValueId();
      id.setMultilingualKey(resolvedKey);
      id.setLangCd(valueDto.getLangCd());

      TMultilingualValue value = new TMultilingualValue();
      value.setId(id);
      value.setMultilingualKey(multilingualBaseRepository.getReferenceById(resolvedKey));
      value.setLangCd(langBase);
      value.setMultilingualVal(valueDto.getMultilingualVal());
      multilingualValueRepository.save(value);
    });

    return new SaveMultilingualResponseDto(resolvedKey, isNew ? "INSERT" : "UPDATE");
  }

  /** 다국어 삭제 */
  @Override
  @Transactional
  public DeleteMultilingualResponseDto delete(DeleteMultilingualRequestDto requestDto) {
    String key = requestDto.getMultilingualKey();
    if (!multilingualBaseRepository.existsById(key)) {
      throw new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + key);
    }
    multilingualRepositoryCustom.deleteAllByMultilingualKey(key);
    multilingualBaseRepository.deleteById(key);
    return new DeleteMultilingualResponseDto(key);
  }
}

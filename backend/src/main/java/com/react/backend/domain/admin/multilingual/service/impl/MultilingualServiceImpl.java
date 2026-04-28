package com.react.backend.api.admin.multilingual.service.impl;

import com.react.backend.api.admin.multilingual.dto.DeleteMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.DeleteMultilingualResponseDto;
import com.react.backend.api.admin.multilingual.dto.SaveMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.SaveMultilingualResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualDetailResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.service.MultilingualService;
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

@Service
@RequiredArgsConstructor
public class MultilingualServiceImpl implements MultilingualService {

  private final MultilingualBaseRepository multilingualBaseRepository;
  private final MultilingualValueRepository multilingualValueRepository;
  private final LangBaseRepository langBaseRepository;

  /** 언어 코드 목록 조회 */
  @Override
  @Transactional(readOnly = true)
  public List<TLangBase> getLangList() {
    return langBaseRepository.findAll();
  }

  /** 다국어 목록 조회 (페이징, 검색 조건) */
  @Override
  @Transactional(readOnly = true)
  public Page<SearchMultilingualListResponseDto> getList(SearchMultilingualRequestDto requestDto) {
    String key   = StringUtils.hasText(requestDto.getMultilingualKey())  ? requestDto.getMultilingualKey()  : null;
    String type  = StringUtils.hasText(requestDto.getMultilingualType()) ? requestDto.getMultilingualType() : null;
    String useYn = StringUtils.hasText(requestDto.getUseYn())            ? requestDto.getUseYn()            : null;

    return multilingualBaseRepository
        .findByCondition(key, type, useYn, PageRequest.of(requestDto.getPage(), requestDto.getPageSize()))
        .map(SearchMultilingualListResponseDto::new);
  }

  /** 다국어 상세 조회 */
  @Override
  @Transactional(readOnly = true)
  public SearchMultilingualDetailResponseDto getDetail(String multilingualKey) {
    TMultilingualBase base = multilingualBaseRepository.findById(multilingualKey)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + multilingualKey));
    List<TMultilingualValue> values = multilingualValueRepository.findAllByMultilingualKey(multilingualKey);
    return new SearchMultilingualDetailResponseDto(base, values);
  }

  /** 다국어 등록/수정 (key 존재 시 수정, 없으면 등록) */
  @Override
  @Transactional
  public SaveMultilingualResponseDto saveOrUpdate(SaveMultilingualRequestDto requestDto) {
    String key = requestDto.getMultilingualKey();
    boolean isNew = !multilingualBaseRepository.existsById(key);

    TMultilingualBase base = isNew ? new TMultilingualBase() : multilingualBaseRepository.findById(key).get();
    base.setMultilingualKey(key);
    base.setMultilingualType(requestDto.getMultilingualType());
    base.setUseYn(requestDto.getUseYn());
    base.setMultilingualDesc(requestDto.getMultilingualDesc());
    multilingualBaseRepository.save(base);

    multilingualValueRepository.deleteAllByMultilingualKey(key);
    requestDto.getValues().forEach(valueDto -> {
      TLangBase langBase = langBaseRepository.findById(valueDto.getLangCd())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 언어 코드입니다: " + valueDto.getLangCd()));

      TMultilingualValueId id = new TMultilingualValueId();
      id.setMultilingualKey(key);
      id.setLangCd(valueDto.getLangCd());

      TMultilingualValue value = new TMultilingualValue();
      value.setId(id);
      value.setMultilingualKey(multilingualBaseRepository.getReferenceById(key));
      value.setLangCd(langBase);
      value.setMultilingualVal(valueDto.getMultilingualVal());
      multilingualValueRepository.save(value);
    });

    return new SaveMultilingualResponseDto(key, isNew ? "INSERT" : "UPDATE");
  }

  /** 다국어 삭제 */
  @Override
  @Transactional
  public DeleteMultilingualResponseDto delete(DeleteMultilingualRequestDto requestDto) {
    String key = requestDto.getMultilingualKey();
    if (!multilingualBaseRepository.existsById(key)) {
      throw new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + key);
    }
    multilingualValueRepository.deleteAllByMultilingualKey(key);
    multilingualBaseRepository.deleteById(key);
    return new DeleteMultilingualResponseDto(key);
  }
}

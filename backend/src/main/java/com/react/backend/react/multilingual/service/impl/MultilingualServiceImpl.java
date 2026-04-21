package com.react.backend.react.multilingual.service.impl;

import com.react.backend.react.multilingual.dto.MultilingualDtlResponseDto;
import com.react.backend.react.multilingual.dto.MultilingualListRequestDto;
import com.react.backend.react.multilingual.dto.MultilingualListResponseDto;
import com.react.backend.react.multilingual.dto.MultilingualSaveRequestDto;
import com.react.backend.react.multilingual.service.MultilingualService;
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

  @Override
  @Transactional(readOnly = true)
  public Page<MultilingualListResponseDto> getList(MultilingualListRequestDto requestDto) {
    String key = StringUtils.hasText(requestDto.getMultilingualKey()) ? requestDto.getMultilingualKey() : null;
    String type = StringUtils.hasText(requestDto.getMultilingualType()) ? requestDto.getMultilingualType() : null;
    String useYn = StringUtils.hasText(requestDto.getUseYn()) ? requestDto.getUseYn() : null;

    return multilingualBaseRepository
        .findByCondition(key, type, useYn, PageRequest.of(requestDto.getPage(), requestDto.getPageSize()))
        .map(MultilingualListResponseDto::new);
  }

  @Override
  @Transactional(readOnly = true)
  public MultilingualDtlResponseDto getDetail(String multilingualKey) {
    TMultilingualBase base = multilingualBaseRepository.findById(multilingualKey)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + multilingualKey));
    List<TMultilingualValue> values = multilingualValueRepository.findAllByMultilingualKey(multilingualKey);
    return new MultilingualDtlResponseDto(base, values);
  }

  @Override
  @Transactional
  public void save(MultilingualSaveRequestDto requestDto) {
    if (multilingualBaseRepository.existsById(requestDto.getMultilingualKey())) {
      throw new IllegalArgumentException("이미 존재하는 다국어 키입니다: " + requestDto.getMultilingualKey());
    }

    TMultilingualBase base = new TMultilingualBase();
    base.setMultilingualKey(requestDto.getMultilingualKey());
    base.setMultilingualType(requestDto.getMultilingualType());
    base.setUseYn(requestDto.getUseYn());
    base.setMultilingualDesc(requestDto.getMultilingualDesc());
    multilingualBaseRepository.save(base);

    saveValues(requestDto.getMultilingualKey(), requestDto);
  }

  @Override
  @Transactional
  public void update(String multilingualKey, MultilingualSaveRequestDto requestDto) {
    TMultilingualBase base = multilingualBaseRepository.findById(multilingualKey)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + multilingualKey));

    base.setMultilingualType(requestDto.getMultilingualType());
    base.setUseYn(requestDto.getUseYn());
    base.setMultilingualDesc(requestDto.getMultilingualDesc());
    multilingualBaseRepository.save(base);

    multilingualValueRepository.deleteAllByMultilingualKey(multilingualKey);
    saveValues(multilingualKey, requestDto);
  }

  @Override
  @Transactional
  public void delete(String multilingualKey) {
    if (!multilingualBaseRepository.existsById(multilingualKey)) {
      throw new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + multilingualKey);
    }
    multilingualValueRepository.deleteAllByMultilingualKey(multilingualKey);
    multilingualBaseRepository.deleteById(multilingualKey);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TLangBase> getLangList() {
    return langBaseRepository.findAll();
  }

  private void saveValues(String multilingualKey, MultilingualSaveRequestDto requestDto) {
    requestDto.getValues().forEach(valueDto -> {
      TLangBase langBase = langBaseRepository.findById(valueDto.getLangCd())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 언어 코드입니다: " + valueDto.getLangCd()));

      TMultilingualValueId id = new TMultilingualValueId();
      id.setMultilingualKey(multilingualKey);
      id.setLangCd(valueDto.getLangCd());

      TMultilingualValue value = new TMultilingualValue();
      value.setId(id);
      value.setMultilingualKey(multilingualBaseRepository.getReferenceById(multilingualKey));
      value.setLangCd(langBase);
      value.setMultilingualVal(valueDto.getMultilingualVal());
      multilingualValueRepository.save(value);
    });
  }
}

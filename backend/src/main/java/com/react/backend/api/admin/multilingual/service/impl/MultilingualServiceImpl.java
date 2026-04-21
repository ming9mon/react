package com.react.backend.api.admin.multilingual.service.impl;

import com.react.backend.api.admin.multilingual.dto.MultilingualDtlResponseDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualSaveRequestDto;
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

  @Override
  @Transactional(readOnly = true)
  public List<TLangBase> getLangList() {
    return langBaseRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MultilingualListResponseDto> getList(MultilingualRequestDto requestDto) {
    String key   = StringUtils.hasText(requestDto.getMultilingualKey())  ? requestDto.getMultilingualKey()  : null;
    String type  = StringUtils.hasText(requestDto.getMultilingualType()) ? requestDto.getMultilingualType() : null;
    String useYn = StringUtils.hasText(requestDto.getUseYn())            ? requestDto.getUseYn()            : null;

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
  public void saveOrUpdate(MultilingualSaveRequestDto requestDto) {
    String key = requestDto.getMultilingualKey();
    TMultilingualBase base = multilingualBaseRepository.findById(key).orElseGet(TMultilingualBase::new);
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
}

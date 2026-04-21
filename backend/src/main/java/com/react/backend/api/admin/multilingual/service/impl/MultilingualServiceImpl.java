package com.react.backend.api.admin.multilingual.service.impl;

import com.react.backend.api.admin.multilingual.dto.MultilingualDtlResponseDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.MultilingualRequestDto;
import com.react.backend.api.admin.multilingual.service.MultilingualService;
import com.react.backend.shared.dto.ResponseDto;
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
  @Transactional
  public ResponseDto handle(MultilingualRequestDto dto) {
    return switch (dto.getMode()) {
      case "LIST"      -> getList(dto);
      case "LANG_LIST" -> getLangList();
      case "DETAIL"    -> getDetail(dto.getMultilingualKey());
      case "SAVE"      -> { saveOrUpdate(dto); yield ResponseDto.builder().code("S000").message("처리되었습니다.").build(); }
      case "DELETE"    -> { delete(dto.getMultilingualKey()); yield ResponseDto.builder().code("S000").message("삭제되었습니다.").build(); }
      default          -> throw new IllegalArgumentException("지원하지 않는 mode입니다: " + dto.getMode());
    };
  }

  private ResponseDto getList(MultilingualRequestDto dto) {
    String key  = StringUtils.hasText(dto.getMultilingualKey())   ? dto.getMultilingualKey()   : null;
    String type = StringUtils.hasText(dto.getMultilingualType())  ? dto.getMultilingualType()  : null;
    String useYn = StringUtils.hasText(dto.getUseYn())            ? dto.getUseYn()             : null;

    Page<MultilingualListResponseDto> result = multilingualBaseRepository
        .findByCondition(key, type, useYn, PageRequest.of(dto.getPage(), dto.getPageSize()))
        .map(MultilingualListResponseDto::new);

    return ResponseDto.builder().code("S000").body(result).build();
  }

  private ResponseDto getLangList() {
    List<TLangBase> list = langBaseRepository.findAll();
    return ResponseDto.builder().code("S000").body(list).build();
  }

  private ResponseDto getDetail(String multilingualKey) {
    TMultilingualBase base = multilingualBaseRepository.findById(multilingualKey)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + multilingualKey));
    List<TMultilingualValue> values = multilingualValueRepository.findAllByMultilingualKey(multilingualKey);
    return ResponseDto.builder().code("S000").body(new MultilingualDtlResponseDto(base, values)).build();
  }

  private void saveOrUpdate(MultilingualRequestDto dto) {
    String key = dto.getMultilingualKey();
    TMultilingualBase base = multilingualBaseRepository.findById(key).orElseGet(TMultilingualBase::new);
    base.setMultilingualKey(key);
    base.setMultilingualType(dto.getMultilingualType());
    base.setUseYn(dto.getUseYn());
    base.setMultilingualDesc(dto.getMultilingualDesc());
    multilingualBaseRepository.save(base);

    multilingualValueRepository.deleteAllByMultilingualKey(key);
    dto.getValues().forEach(valueDto -> {
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

  private void delete(String multilingualKey) {
    if (!multilingualBaseRepository.existsById(multilingualKey)) {
      throw new IllegalArgumentException("존재하지 않는 다국어 키입니다: " + multilingualKey);
    }
    multilingualValueRepository.deleteAllByMultilingualKey(multilingualKey);
    multilingualBaseRepository.deleteById(multilingualKey);
  }
}

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

import java.util.List;

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

  /** 다국어 등록/수정 (key 존재 시 수정, 없으면 등록) */
  @Override
  @Transactional
  public SaveMultilingualResponseDto save(SaveMultilingualRequestDto requestDto) {
    String key = requestDto.getMultilingualKey();
    boolean isNew = !multilingualBaseRepository.existsById(key);

    TMultilingualBase base = isNew ? new TMultilingualBase() : multilingualBaseRepository.findById(key).get();
    base.setMultilingualKey(key);
    base.setMultilingualType(requestDto.getMultilingualType());
    base.setUseYn(requestDto.getUseYn());
    base.setMultilingualDesc(requestDto.getMultilingualDesc());
    multilingualBaseRepository.save(base);

    multilingualRepositoryCustom.deleteAllByMultilingualKey(key);
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
    multilingualRepositoryCustom.deleteAllByMultilingualKey(key);
    multilingualBaseRepository.deleteById(key);
    return new DeleteMultilingualResponseDto(key);
  }
}

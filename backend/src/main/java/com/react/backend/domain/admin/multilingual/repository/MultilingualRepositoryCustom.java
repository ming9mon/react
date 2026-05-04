package com.react.backend.domain.admin.multilingual.repository;

import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualListResponseDto;
import com.react.backend.shared.entity.TMultilingualValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MultilingualRepositoryCustom {

    Page<SearchMultilingualListResponseDto> searchMultilingualList(
        String langCd,
        String multilingualKey,
        String multilingualType,
        String multilingualVal,
        Pageable pageable
    );

    List<TMultilingualValue> findAllByMultilingualKey(String multilingualKey);

    void deleteAllByMultilingualKey(String multilingualKey);

    long nextSequenceValue(String sequenceName);
}

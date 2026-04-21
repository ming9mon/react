package com.react.backend.api.admin.multilingual.controller;

import com.react.backend.api.admin.multilingual.dto.DeleteMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.DeleteMultilingualResponseDto;
import com.react.backend.api.admin.multilingual.dto.SaveMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.SaveMultilingualResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualDetailResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.SearchMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.service.MultilingualService;
import com.react.backend.shared.entity.TLangBase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/multilingual")
@RequiredArgsConstructor
public class MultilingualController {

  private final MultilingualService multilingualService;

  /** 언어 코드 목록 조회 */
  @GetMapping("/lang")
  public List<TLangBase> getLangList() {
    return multilingualService.getLangList();
  }

  /** 다국어 목록 조회 (페이징, 검색 조건) */
  @GetMapping
  public Page<SearchMultilingualListResponseDto> getList(SearchMultilingualRequestDto requestDto) {
    return multilingualService.getList(requestDto);
  }

  /** 다국어 상세 조회 */
  @GetMapping("/{multilingualKey}")
  public SearchMultilingualDetailResponseDto getDetail(@PathVariable String multilingualKey) {
    return multilingualService.getDetail(multilingualKey);
  }

  /** 다국어 등록/수정 (key 존재 시 수정, 없으면 등록) */
  @PostMapping
  public SaveMultilingualResponseDto saveOrUpdate(@Validated @RequestBody SaveMultilingualRequestDto requestDto) {
    return multilingualService.saveOrUpdate(requestDto);
  }

  /** 다국어 삭제 */
  @DeleteMapping("/{multilingualKey}")
  public DeleteMultilingualResponseDto delete(@PathVariable String multilingualKey) {
    return multilingualService.delete(new DeleteMultilingualRequestDto(multilingualKey));
  }
}

package com.react.backend.api.admin.multilingual.controller;

import com.react.backend.api.admin.multilingual.dto.SaveMultilingualRequestDto;
import com.react.backend.api.admin.multilingual.dto.SelectMultilingualDtlResponseDto;
import com.react.backend.api.admin.multilingual.dto.SelectMultilingualListResponseDto;
import com.react.backend.api.admin.multilingual.dto.SelectMultilingualRequestDto;
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

  @GetMapping("/lang")
  public List<TLangBase> getLangList() {
    return multilingualService.getLangList();
  }

  @GetMapping
  public Page<SelectMultilingualListResponseDto> getList(SelectMultilingualRequestDto requestDto) {
    return multilingualService.getList(requestDto);
  }

  @GetMapping("/{multilingualKey}")
  public SelectMultilingualDtlResponseDto getDetail(@PathVariable String multilingualKey) {
    return multilingualService.getDetail(multilingualKey);
  }

  @PostMapping
  public void saveOrUpdate(@Validated @RequestBody SaveMultilingualRequestDto requestDto) {
    multilingualService.saveOrUpdate(requestDto);
  }

  @DeleteMapping("/{multilingualKey}")
  public void delete(@PathVariable String multilingualKey) {
    multilingualService.delete(multilingualKey);
  }
}

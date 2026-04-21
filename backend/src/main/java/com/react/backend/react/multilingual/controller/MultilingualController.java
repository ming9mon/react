package com.react.backend.react.multilingual.controller;

import com.react.backend.react.multilingual.dto.MultilingualDtlResponseDto;
import com.react.backend.react.multilingual.dto.MultilingualListRequestDto;
import com.react.backend.react.multilingual.dto.MultilingualListResponseDto;
import com.react.backend.react.multilingual.dto.MultilingualSaveRequestDto;
import com.react.backend.react.multilingual.service.MultilingualService;
import com.react.backend.shared.entity.TLangBase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/multilingual")
@RequiredArgsConstructor
public class MultilingualController {

  private final MultilingualService multilingualService;

  @GetMapping("/lang")
  public List<TLangBase> getLangList() {
    return multilingualService.getLangList();
  }

  @PostMapping("/list")
  public Page<MultilingualListResponseDto> getList(@RequestBody MultilingualListRequestDto requestDto) {
    return multilingualService.getList(requestDto);
  }

  @GetMapping("/{multilingualKey}")
  public MultilingualDtlResponseDto getDetail(@PathVariable String multilingualKey) {
    return multilingualService.getDetail(multilingualKey);
  }

  @PostMapping
  public void save(@Validated @RequestBody MultilingualSaveRequestDto requestDto) {
    multilingualService.save(requestDto);
  }

  @PutMapping("/{multilingualKey}")
  public void update(@PathVariable String multilingualKey,
                     @Validated @RequestBody MultilingualSaveRequestDto requestDto) {
    multilingualService.update(multilingualKey, requestDto);
  }

  @DeleteMapping("/{multilingualKey}")
  public void delete(@PathVariable String multilingualKey) {
    multilingualService.delete(multilingualKey);
  }
}

package com.react.backend.shared.service;

import com.react.backend.shared.dto.FileSaveResultDto;
import com.react.backend.shared.enums.FileType;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

  /**
   * 파일 저장
   * @param file MultipartFile
   * @param type 업로드 타입
   * @return FileSaveResultDto
   */
  FileSaveResultDto fileSave(MultipartFile file, FileType type) throws Exception;
}

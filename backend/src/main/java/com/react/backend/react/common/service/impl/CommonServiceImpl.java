package com.react.backend.react.common.service.impl;

import com.react.backend.configuration.exception.RestException;
import com.react.backend.configuration.util.FileTypeValidator;
import com.react.backend.react.common.dto.FileSaveResultDto;
import com.react.backend.react.common.enums.FileType;
import com.react.backend.react.common.service.CommonService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {

  @Value("${file.upload.profile.path}")
  private String uploadProfilePath;

  /**
   * 파일 저장
   * @param file MultipartFile
   * @param type 업로드 타입
   * @return FileSaveResultDto
   */
  @Override
  public FileSaveResultDto fileSave(MultipartFile file, FileType type) throws Exception {
    // TO-DO 파일 사이즈 체크 or 리사이징
    
    // 파일 확장자 및 MINE 타입 체크
    boolean isAllowedFile = FileTypeValidator.isAllowedFile(file, type);
    
    if (!isAllowedFile) {
      throw new RestException("허용하지 않는 파일입니다.<br>확인 후 다시 시도해주시기 바랍니다.");
    }
    
    String orgFileName = file.getOriginalFilename();
    String ext = Objects.requireNonNull(FilenameUtils.getExtension(orgFileName)).toLowerCase();
    String savedFileName = UUID.randomUUID().toString().replace("-", "") + "." + ext;
    Path savePath = null;

    // 프로필 이미지 업로드 경로
    if (type == FileType.PROFILE_IMAGE) {
      Paths.get(uploadProfilePath, savedFileName);
    }

    // 디렉토리가 없으면 생성
    Files.createDirectories(savePath.getParent());

    // 파일 저장
    file.transferTo(savePath.toFile());

    return FileSaveResultDto.builder()
        .orgFileName(orgFileName)
        .savedFileName(savedFileName)
        .savedFilePath(savePath.getParent().toString())
        .build();
  }
}

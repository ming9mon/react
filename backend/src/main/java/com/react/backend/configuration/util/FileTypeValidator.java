package com.react.backend.configuration.util;

import com.react.backend.react.common.enums.FileType;
import com.react.backend.react.common.enums.ImageFileType;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class FileTypeValidator {
  private static final Tika tika = new Tika();

  public static boolean isAllowedFile(MultipartFile file, FileType fileType) throws Exception {
    // 확장자 추출
    String originalName = file.getOriginalFilename();

    if (originalName == null) return false;

    String ext = FilenameUtils.getExtension(originalName).toLowerCase();

    Optional<ImageFileType> typeOpt = Optional.empty();

    if (fileType == FileType.IMAGE || fileType == FileType.PROFILE_IMAGE) {
      typeOpt = ImageFileType.fromExtension(ext);
    }

    // enum에 존재하는 확장자인지 확인
    if (typeOpt.isEmpty()) { // 지원하지 않는 확장자
      return false;
    }

    // MIME 타입 검증
    String actualMimeType = tika.detect(file.getInputStream());
    String expectedMimeType = typeOpt.get().getMimeType();

    return expectedMimeType.equalsIgnoreCase(actualMimeType);
  }
}

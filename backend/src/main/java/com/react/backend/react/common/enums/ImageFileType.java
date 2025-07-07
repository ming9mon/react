package com.react.backend.react.common.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ImageFileType {
  JPG("jpg", "image/jpeg"),
  JPEG("jpeg", "image/jpeg"),
  PNG("png", "image/png"),
  GIF("gif", "image/gif"),
  WEBP("webp", "image/webp");

  private final String extension;
  private final String mimeType;

  ImageFileType(String extension, String mimeType) {
    this.extension = extension;
    this.mimeType = mimeType;
  }

  public String getMimeType() {
    return mimeType;
  }

  public static Optional<ImageFileType> fromExtension(String ext) {
    return Arrays.stream(values())
        .filter(type -> type.extension.equalsIgnoreCase(ext))
        .findFirst();
  }
}
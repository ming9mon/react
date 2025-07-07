package com.react.backend.react.auth.dto;

import com.react.backend.react.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignUpReqDto extends BaseDto {
  String userId;
  String passwd;
  String userNm;
  String nickname;
  String email;
  MultipartFile profileImg;
}

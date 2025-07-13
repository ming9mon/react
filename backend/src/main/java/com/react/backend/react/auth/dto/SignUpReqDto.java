package com.react.backend.react.auth.dto;

import com.react.backend.react.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignUpReqDto extends BaseDto {
  String userId;
  String passWd;
  String userNm;
  String nickname;
  String sex;
  String email;
  MultipartFile profileImg;
}

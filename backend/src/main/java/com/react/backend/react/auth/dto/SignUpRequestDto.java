package com.react.backend.react.auth.dto;

import com.react.backend.react.common.dto.BaseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignUpRequestDto extends BaseDto {
  @NotBlank(message = "아이디는 필수입니다.")
  @Size(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
  String userId;

  @NotBlank(message = "비밀번호는 필수입니다.")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+]).{8,}$",
      message = "비밀번호는 대문자, 숫자, 특수문자를 포함한 8자리 이상이어야 합니다.")
  String passWd;

  @NotBlank(message = "이름은 필수입니다.")
  @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "이름은 한글, 영문만 가능합니다.")
  String userNm;

  @NotBlank(message = "닉네임은 필수입니다.")
  String nickname;

  @NotBlank(message = "성별은 필수입니다.")
  String sex;

  @Email(message = "유효한 이메일 형식이 아닙니다.")
  @NotBlank(message = "이메일은 필수입니다.")
  String email;

  MultipartFile profileImg;
}

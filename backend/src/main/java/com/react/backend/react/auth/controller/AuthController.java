package com.react.backend.react.auth.controller;

import com.react.backend.configuration.util.JwtUtil;
import com.react.backend.react.auth.dto.KakaoLoginRequestDto;
import com.react.backend.react.auth.dto.NaverLoginRequestDto;
import com.react.backend.react.auth.dto.SignUpRequestDto;
import com.react.backend.react.auth.service.LoginService;
import com.react.backend.react.common.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final LoginService loginService;

    /**
     * 일반 로그인
     * @return 공통정보 return
     */
    @PostMapping("/login")
    public Map<String, String> login() {

        UserInfoDto userInfoDto = new UserInfoDto();

        String accessToken = jwtUtil.generateAccessToken(userInfoDto);
        String refreshToken = jwtUtil.generateRefreshToken(userInfoDto);

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    @PostMapping("/kakao")
    public Map<String, Object> kakaoLogin(@RequestBody KakaoLoginRequestDto kakaoLoginRequestDto) throws Exception {
        return loginService.kakaoLogin(kakaoLoginRequestDto);
    }

    /**
     * 네이버 로그인
     * @param naverLoginRequestDto
     * @return
     * @throws Exception
     */
    @PostMapping("/naver")
    public String naverLogin(@RequestBody NaverLoginRequestDto naverLoginRequestDto) throws Exception {
        return loginService.naverLogin(naverLoginRequestDto);
    }

    /**
     * 회원가입
     * @param signUpRequestDto
     * @return
     * @throws Exception
     */
    @PostMapping("/signup")
    public void signUp(@Validated @ModelAttribute SignUpRequestDto signUpRequestDto) throws Exception {
        loginService.signUp(signUpRequestDto);
    }

    @PostMapping("/logout")
    public String logout() {
        return null;
    }
}

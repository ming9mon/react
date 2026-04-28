package com.react.backend.api.auth.controller;

import com.react.backend.api.auth.dto.*;
import com.react.backend.api.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    /**
     * 일반 로그인
     * @return tokenInfo, userInfo, userAuth
     */
    @PostMapping("/login")
    public LoginResponseDto login(@Validated @RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return loginService.login(loginRequestDto);
    }

    @PostMapping("/kakao")
    public Map<String, Object> kakaoLogin(@RequestBody KakaoLoginRequestDto kakaoLoginRequestDto) throws Exception {
        return loginService.kakaoLogin(kakaoLoginRequestDto);
    }

    /**
     * 네이버 로그인
     */
    @PostMapping("/naver")
    public String naverLogin(@RequestBody NaverLoginRequestDto naverLoginRequestDto) throws Exception {
        return loginService.naverLogin(naverLoginRequestDto);
    }

    /**
     * 회원가입
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

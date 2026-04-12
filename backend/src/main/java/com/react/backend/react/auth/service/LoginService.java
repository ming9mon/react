package com.react.backend.react.auth.service;

import com.react.backend.react.auth.dto.*;

import java.util.Map;

public interface LoginService {

    /**
     * 일반 로그인
     */
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    /**
     * 카카오 로그인
     */
    Map<String, Object> kakaoLogin(KakaoLoginRequestDto kakaoLoginRequestDto) throws Exception;

    /**
     * 네이버 로그인
     */
    String naverLogin(NaverLoginRequestDto naverLoginRequestDto) throws Exception;

    /**
     * 회원 가입
     */
    void signUp(SignUpRequestDto signUpRequestDto) throws Exception;
}

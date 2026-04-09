package com.react.backend.react.auth.service;

import com.react.backend.react.auth.dto.KakaoLoginRequestDto;
import com.react.backend.react.auth.dto.NaverLoginRequestDto;
import com.react.backend.react.auth.dto.SignUpRequestDto;

import java.util.Map;

public interface LoginService {

    /**
     * 카카오 로그인
     * @param kakaoLoginRequestDto
     * @return
     * @throws Exception
     */
    Map<String, Object> kakaoLogin(KakaoLoginRequestDto kakaoLoginRequestDto) throws Exception;

    /**
     * 네이버 로그인
     * @param naverLoginRequestDto
     * @return
     * @throws Exception
     */
    String naverLogin(NaverLoginRequestDto naverLoginRequestDto) throws Exception;

    /**
     * 회원 가입
     * @param signUpRequestDto
     * @throws Exception
     */
    void signUp(SignUpRequestDto signUpRequestDto) throws Exception;
}

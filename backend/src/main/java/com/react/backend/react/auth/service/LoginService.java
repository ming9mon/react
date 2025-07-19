package com.react.backend.react.auth.service;

import com.react.backend.react.auth.dto.KakaoLoginReqDto;
import com.react.backend.react.auth.dto.NaverLoginReqDto;
import com.react.backend.react.auth.dto.SignUpReqDto;
import com.react.backend.react.common.dto.ResponseDto;

import java.util.Map;

public interface LoginService {

    /**
     * 카카오 로그인
     * @param kakaoLoginReqDto
     * @return
     * @throws Exception
     */
    Map<String, Object> kakaoLogin(KakaoLoginReqDto kakaoLoginReqDto) throws Exception;

    /**
     * 네이버 로그인
     * @param naverLoginReqDto
     * @return
     * @throws Exception
     */
    String naverLogin(NaverLoginReqDto naverLoginReqDto) throws Exception;

    /**
     * 회원 가입
     * @param signUpReqDto
     * @throws Exception
     */
    void signUp(SignUpReqDto signUpReqDto) throws Exception;
}

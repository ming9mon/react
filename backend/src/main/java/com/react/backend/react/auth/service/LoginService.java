package com.react.backend.react.auth.service;

import com.react.backend.react.auth.dto.KakaoLoginReqDto;
import com.react.backend.react.auth.dto.NaverLoginReqDto;

import java.util.Map;

public interface LoginService {

    /**
     * 카카오 로그인
     * @param kakaoLoginReqDto
     * @return
     * @throws Exception
     */
    Map<String, Object> kakaoLogin(KakaoLoginReqDto kakaoLoginReqDto) throws Exception;

    String naverLogin(NaverLoginReqDto naverLoginReqDto) throws Exception;
}

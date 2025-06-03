package com.react.backend.react.auth.controller;

import com.react.backend.configuration.util.JwtUtil;
import com.react.backend.react.auth.dto.KakaoLoginReqDto;
import com.react.backend.react.auth.dto.NaverLoginReqDto;
import com.react.backend.react.auth.service.LoginService;
import com.react.backend.react.common.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginService loginService;

    /**
     * 일반 로그인
     * @return 공통정보 return
     */
    @PostMapping("/login")
    public Map<String, String> login() {

        UserInfoDto userInfoDto = new UserInfoDto();

        String accessToken = jwtUtil.generateAccessToken(userInfoDto);
        String refreshToken = jwtUtil.generateRefreshToken(userInfoDto);

        System.out.println(accessToken);
        System.out.println(refreshToken);

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    @PostMapping("/login/kakao")
    public Map<String, Object> kakaoLogin(@RequestBody KakaoLoginReqDto kakaoLoginReqDto) throws Exception {
        return loginService.kakaoLogin(kakaoLoginReqDto);
    }

    /**
     * 네이버 로그인
     * @param naverLoginReqDto
     * @return
     * @throws Exception
     */
    @PostMapping("/login/naver")
    public String naverLogin(@RequestBody NaverLoginReqDto naverLoginReqDto) throws Exception {
        return loginService.naverLogin(naverLoginReqDto);
    }

    @PostMapping("/logout")
    public String logout() {
        return null;
    }

    @PostMapping("/signUp")
    public String signUp() {
        return null;
    }
}

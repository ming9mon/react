package com.react.backend.react.auth.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.backend.react.auth.dto.KakaoLoginReqDto;
import com.react.backend.react.auth.dto.NaverLoginReqDto;
import com.react.backend.react.auth.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Value("${kakao.login.info.url}")
    private String KAKAO_USER_INFO_URL;

    @Value("${naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client-secret}")
    private String NAVER_CLIENT_SECRET;


    public Map<String, Object> kakaoLogin(KakaoLoginReqDto kakaoLoginReqDto) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = kakaoLoginReqDto.getAccessToken();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 카카오 API 호출
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_USER_INFO_URL, HttpMethod.GET, entity, String.class);

        // 응답 파싱
        Map<String, Object> userInfo = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            JsonNode kakaoAccount = rootNode.path("kakao_account");
            JsonNode profile = kakaoAccount.path("profile");

            System.out.println(profile);

            userInfo.put("id", rootNode.path("id").asText());
            userInfo.put("nickname", profile.path("nickname").asText());
            userInfo.put("profile_image", profile.path("thumbnail_image_url").asText());
            userInfo.put("email", kakaoAccount.path("email").asText());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }


    public String naverLogin(NaverLoginReqDto naverLoginReqDto) throws Exception {
        String code = naverLoginReqDto.getCode();

        // 1. 네이버에 access token 요청
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?"
                + "grant_type=authorization_code"
                + "&client_id=" + NAVER_CLIENT_ID
                + "&client_secret=" + NAVER_CLIENT_SECRET
                + "&code=" + code;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.GET, null, Map.class);

        System.out.println(tokenUrl);
        if (tokenResponse.getBody() == null || tokenResponse.getBody().get("access_token") == null) {
            System.out.println("토큰 발급 실패");
            return null;
        }

        String accessToken = (String) tokenResponse.getBody().get("access_token");

        // 2. 네이버에 사용자 정보 요청
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);

        System.out.println(userResponse.getBody());
        //return ResponseEntity.ok(userResponse.getBody());
        return code;
    }
}

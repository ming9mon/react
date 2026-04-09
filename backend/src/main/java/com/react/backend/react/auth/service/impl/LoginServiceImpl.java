package com.react.backend.react.auth.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.backend.react.auth.domain.User;
import com.react.backend.react.auth.dto.KakaoLoginRequestDto;
import com.react.backend.react.auth.dto.NaverLoginRequestDto;
import com.react.backend.react.auth.dto.SignUpRequestDto;
import com.react.backend.react.auth.repository.UserRepository;
import com.react.backend.react.auth.service.LoginService;
import com.react.backend.react.common.dto.FileSaveResultDto;
import com.react.backend.react.common.enums.FileType;
import com.react.backend.react.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final CommonService commonService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kakao.login.info.url}")
    private String kakaoUserInfoUrl;

    @Value("${naver.client-id}")
    private String naverClientId;

    @Value("${naver.client-secret}")
    private String naverClientSecret;


    /**
     * 카카오 로그인
     * @param kakaoLoginRequestDto
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> kakaoLogin(KakaoLoginRequestDto kakaoLoginRequestDto) throws Exception {
        String accessToken = kakaoLoginRequestDto.getAccessToken();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 카카오 API 호출
        ResponseEntity<String> response = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, entity, String.class);

        // 응답 파싱
        Map<String, Object> userInfo = new HashMap<>();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            JsonNode kakaoAccount = rootNode.path("kakao_account");
            JsonNode profile = kakaoAccount.path("profile");

            log.debug("kakao profile: {}", profile);

            userInfo.put("id", rootNode.path("id").asText());
            userInfo.put("nickname", profile.path("nickname").asText());
            userInfo.put("profile_image", profile.path("thumbnail_image_url").asText());
            userInfo.put("email", kakaoAccount.path("email").asText());

        } catch (Exception e) {
            log.error("카카오 사용자 정보 파싱 실패", e);
        }
        return userInfo;
    }

    /**
     * 네이버 로그인
     * @param naverLoginRequestDto
     * @return
     * @throws Exception
     */
    @Override
    public String naverLogin(NaverLoginRequestDto naverLoginRequestDto) throws Exception {
        String code = naverLoginRequestDto.getCode();

        // 1. 네이버에 access token 요청
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?"
                + "grant_type=authorization_code"
                + "&client_id=" + naverClientId
                + "&client_secret=" + naverClientSecret
                + "&code=" + code;

        ResponseEntity<Map> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.GET, null, Map.class);

        if (tokenResponse.getBody() == null || tokenResponse.getBody().get("access_token") == null) {
            log.warn("네이버 토큰 발급 실패 - code: {}", code);
            return null;
        }

        String naverAccessToken = (String) tokenResponse.getBody().get("access_token");

        // 2. 네이버에 사용자 정보 요청
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + naverAccessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);

        log.debug("naver user info: {}", userResponse.getBody());
        return code;
    }

    /**
     * 회원가입
     * @param signUpRequestDto
     * @throws Exception
     */
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) throws Exception {
        if (userRepository.existsByUserId(signUpRequestDto.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 EMAIL입니다.");
        }

        // 파일이 있으면 저장
        String profileImgUrl = null;
        if (signUpRequestDto.getProfileImg() != null) {
            FileSaveResultDto fileSaveResult = commonService.fileSave(signUpRequestDto.getProfileImg(), FileType.PROFILE_IMAGE);
            profileImgUrl = fileSaveResult.getSavedFilePath() + File.separator + fileSaveResult.getSavedFileName();
        }

        User user = User.builder()
            .userId(signUpRequestDto.getUserId())
            .passWd(passwordEncoder.encode(signUpRequestDto.getPassWd()))
            .userNm(signUpRequestDto.getUserNm())
            .nickname(signUpRequestDto.getNickname())
            .sex(User.Sex.valueOf(signUpRequestDto.getSex()))
            .email(signUpRequestDto.getEmail())
            .profileImgUrl(profileImgUrl)
            .provider(User.Provider.LOCAL)
            .build();

        userRepository.save(user);
    }
}

package com.react.backend.react.auth.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.backend.react.auth.dto.*;
import com.react.backend.react.auth.repository.UserRepository;
import com.react.backend.react.auth.service.LoginService;
import com.react.backend.shared.dto.FileSaveResultDto;
import com.react.backend.shared.dto.TokenInfoDto;
import com.react.backend.shared.dto.UserInfoDto;
import com.react.backend.shared.enums.FileType;
import com.react.backend.shared.service.CommonService;
import com.react.backend.shared.entity.TUser;
import com.react.backend.shared.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
    private final JwtUtil jwtUtil;

    @Value("${kakao.login.info.url}")
    private String kakaoUserInfoUrl;

    @Value("${naver.client-id}")
    private String naverClientId;

    @Value("${naver.client-secret}")
    private String naverClientSecret;


    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        TUser user = userRepository.findByUserId(loginRequestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPassWd(), user.getPasswd())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setUserNm(user.getUserNm());
        userInfoDto.setNickname(user.getNickname());

        String accessToken = jwtUtil.generateAccessToken(userInfoDto);
        String refreshToken = jwtUtil.generateRefreshToken(userInfoDto);

        // refreshToken DB 저장
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return LoginResponseDto.builder()
                .tokenInfo(TokenInfoDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
                .userInfo(UserInfoDto.builder()
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .nickname(user.getNickname())
                        .build())
                .userAuth(null) // TODO
                .build();
    }

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

        TUser user = new TUser();
        user.setUserId(signUpRequestDto.getUserId());
        user.setPasswd(passwordEncoder.encode(signUpRequestDto.getPassWd()));
        user.setUserNm(signUpRequestDto.getUserNm());
        user.setNickname(signUpRequestDto.getNickname());
        user.setSex(signUpRequestDto.getSex());
        user.setEmail(signUpRequestDto.getEmail());
        user.setProfileImgUrl(profileImgUrl);
        user.setProvider('L'); // LOCAL

        userRepository.save(user);
    }
}
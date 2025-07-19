package com.react.backend.react.auth.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.backend.react.auth.domain.User;
import com.react.backend.react.auth.dto.KakaoLoginReqDto;
import com.react.backend.react.auth.dto.NaverLoginReqDto;
import com.react.backend.react.auth.dto.SignUpReqDto;
import com.react.backend.react.auth.repository.UserRepository;
import com.react.backend.react.auth.service.LoginService;
import com.react.backend.react.common.dto.FileSaveResultDto;
import com.react.backend.react.common.dto.ResponseDto;
import com.react.backend.react.common.enums.FileType;
import com.react.backend.react.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final CommonService commonService;
    private final UserRepository userRepository;

    @Value("${kakao.login.info.url}")
    private String KAKAO_USER_INFO_URL;

    @Value("${naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client-secret}")
    private String NAVER_CLIENT_SECRET;


    /**
     * 카카오 로그인
     * @param kakaoLoginReqDto
     * @return
     * @throws Exception
     */
    @Override
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

    /**
     * 네이버 로그인
     * @param naverLoginReqDto
     * @return
     * @throws Exception
     */
    @Override
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

    /**
     *
     * @param signUpReqDto
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDto signUp(SignUpReqDto signUpReqDto) throws Exception {
        if (userRepository.existsByUserId(signUpReqDto.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }
        if (userRepository.existsByEmail(signUpReqDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 EMAIL입니다.");
        }

        // 파일이 있으면 저장
        String profileImgUrl = null;
        if (signUpReqDto.getProfileImg() != null) {
            FileSaveResultDto fileSaveResult = commonService.fileSave(signUpReqDto.getProfileImg(), FileType.PROFILE_IMAGE);

            profileImgUrl = fileSaveResult.getSavedFilePath() + File.separator + fileSaveResult.getSavedFileName();
        }

        // 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePasswd = passwordEncoder.encode(signUpReqDto.getPassWd());

        User user = User.builder()
            .userId(signUpReqDto.getUserId())
            .passWd(encodePasswd)
            .userNm(signUpReqDto.getUserNm())
            .nickname(signUpReqDto.getNickname())
            .sex(User.Sex.valueOf(signUpReqDto.getSex()))
            .email(signUpReqDto.getEmail())
            .profileImgUrl(profileImgUrl)
            .provider(User.Provider.local)
            .build();

        userRepository.save(user);

        return ResponseDto.builder()
            .message("회원가입 성공하였습니다.")
            .build();
    }
}

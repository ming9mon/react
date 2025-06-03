package com.react.backend.configuration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.backend.react.common.dto.UserInfoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretKey.getBytes();
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 액세스 토큰 생성
    public String generateAccessToken(UserInfoDto userInfo) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userInfo", userInfo);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis())) // 발행 시간 설정
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration)) // 만료 시간 설정
                .signWith(signingKey) // 서명 설정
                .compact(); // 토큰 생성
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(UserInfoDto userInfo) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userInfo", userInfo);

        return Jwts.builder()
                .claim("userId", userInfo.getUserId())
                .claim("role", "test")
                .issuedAt(new Date(System.currentTimeMillis())) // 발행 시간 설정
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) // 만료 시간 설정
                .signWith(signingKey) // 서명 설정
                .compact(); // 토큰 생성
    }

    /**
     * 헤더에서 토큰 추출
     * @param request
     * @return 토큰 || null
     */
    public String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        } else return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("role"));
        return new UsernamePasswordAuthenticationToken(claims.get("username"), token, authorities);
    }

    // 토큰에서 클레임 추출
    public Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(signingKey)
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }

    /**
     * 헤더에서 토큰 값 추출하여 사용자 정보 get
     * @param request
     * @return
     */
    public UserInfoDto getUserInfo(HttpServletRequest request) {
        String token = getToken(request);

        Claims claims = Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        ObjectMapper mapper = new ObjectMapper();

        return mapper.convertValue(claims.get("userInfo"), UserInfoDto.class);
    }

    // 토큰 만료 여부 검사
    public boolean isTokenExpired(String token) {
//        Date expiration = extractAllClaims(token).getExpiration();
//        expiration.before(new Date());

        try{
            extractAllClaims(token);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}

package com.react.backend.configuration.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", "test")
                .issuedAt(new Date(System.currentTimeMillis())) // 발행 시간 설정
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration)) // 만료 시간 설정
                .signWith(signingKey) // 서명 설정
                .compact(); // 토큰 생성
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", "test")
                .issuedAt(new Date(System.currentTimeMillis())) // 발행 시간 설정
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) // 만료 시간 설정
                .signWith(signingKey) // 서명 설정
                .compact(); // 토큰 생성
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

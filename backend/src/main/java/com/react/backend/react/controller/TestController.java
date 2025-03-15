package com.react.backend.react.controller;

import com.react.backend.configuration.util.JwtUtil;
import com.react.backend.react.model.Testaa;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    private final JwtUtil jwtUtil;

    public TestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> tokenTest(@RequestBody Testaa test) {
        String accessToken = jwtUtil.generateAccessToken("test");
        String refreshToken = jwtUtil.generateRefreshToken("test");

        System.out.println(accessToken);
        System.out.println(refreshToken);

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    @PostMapping(value = "/test")
    public String test(@RequestBody Testaa test) {
        return "test";
    }
}

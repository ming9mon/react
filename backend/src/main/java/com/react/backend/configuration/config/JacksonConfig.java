package com.react.backend.configuration.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.react.backend.configuration.util.EscapeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        // 모든 String 타입 이스케이프 처리
        module.addDeserializer(String.class, new EscapeUtil());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
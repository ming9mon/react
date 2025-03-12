package com.react.backend.configuration.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.react.backend.configuration.serializer.XSSStringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        // 모든 String 타입에 대해 XSS deserializer 적용
        module.addDeserializer(String.class, new XSSStringDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
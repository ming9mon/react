package com.react.backend.configuration.config;

import com.react.backend.configuration.exception.GlobalExceptionHandler;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResponseConfig implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // 에러는 건들지 않음
        if (body instanceof GlobalExceptionHandler.ErrorResponse) {
            return body;
        }

        // 파일 다운로드는 건들지 않음
        if (request.getURI().getPath().contains("/fileDown")) {
            return body;
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", "200");
        responseBody.put("message", "");

        if (body instanceof Map<?, ?>) {
            String code = (String) ((Map<?, ?>) body).get("code");
            String message = (String) ((Map<?, ?>) body).get("message");

            if (code != null && !"".equals(code)) {
                responseBody.put("code", code);
                responseBody.put("message", message);

                ((Map<?, ?>) body).remove("code");
                ((Map<?, ?>) body).remove("message");
            }
        }

        responseBody.put("body", body);
        return responseBody;
    }
}
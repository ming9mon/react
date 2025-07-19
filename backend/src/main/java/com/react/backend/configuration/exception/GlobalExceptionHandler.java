package com.react.backend.configuration.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("EM400", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { RestException.class })
    public ResponseEntity<ErrorResponse> handleRestException(RestException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse("EM500", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // JWT 유효성 검사 실패 시 예외 처리
    @ExceptionHandler(value = { JwtValidationException.class })
    public ResponseEntity<ErrorResponse> handleJwtValidationException(JwtValidationException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse("401", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSize(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(new ErrorResponse("EM413", "파일 크기가 너무 큽니다. 최대 10MB까지 업로드 가능합니다."), HttpStatus.PAYLOAD_TOO_LARGE);
    }


    // 인증 실패 예외 처리
//    @ExceptionHandler(value = { AuthenticationException.class })
//    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
//        return new ResponseEntity<>(new ErrorResponse("500", "Authentication Failed"), HttpStatus.FORBIDDEN);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        String message = "에러발생";
        return new ResponseEntity<>(new ErrorResponse("E9999", message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}

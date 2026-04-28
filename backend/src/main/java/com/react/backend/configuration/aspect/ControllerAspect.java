package com.react.backend.configuration.aspect;

import com.react.backend.shared.dto.UserInfoDto;
import com.react.backend.shared.dto.BaseDto;
import com.react.backend.shared.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class ControllerAspect {

    private final JwtUtil jwtUtil;

    @Before("execution(* com.react.backend.domain..controller..*(..))")
    public void beforeController(JoinPoint joinPoint) {
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
       String token = jwtUtil.getToken(request);
       String accessIp = request.getRemoteAddr();

       // dto에 기본 정보 세팅
       for (Object arg : joinPoint.getArgs()) {
           if (arg instanceof BaseDto dto) {
               UserInfoDto userInfo = new UserInfoDto();

               if (token != null && !token.isEmpty()) {
                   userInfo = jwtUtil.getUserInfo(request);
               }

               dto.setSessionUserSeq(userInfo.getUserSeq());
               dto.setAccessIp(accessIp);
           }
       }
    }
}

package com.meetry.backend.aspect;

import com.meetry.backend.aspect.annotation.Authenticated;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.helper.AuthHelper;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@AllArgsConstructor
public class AuthenticatedInterceptor {

    private final AuthHelper authHelper;

    @Around("@annotation(com.meetry.backend.aspect.annotation.Authenticated)")
    public Object authenticate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        HttpServletRequest httpServletRequest = (HttpServletRequest) proceedingJoinPoint.getArgs()[0];
        List<Role> role = getAnnotationValue(proceedingJoinPoint);
        authHelper.authenticate(httpServletRequest, role);
        return proceedingJoinPoint.proceed();
    }

    private List<Role> getAnnotationValue(ProceedingJoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Authenticated authenticated = method.getAnnotation(Authenticated.class);
        return Arrays.asList(authenticated.value());
    }

}

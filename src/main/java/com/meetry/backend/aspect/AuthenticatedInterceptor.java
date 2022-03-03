package com.meetry.backend.aspect;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.helper.AuthHelper;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Aspect
@Component
@AllArgsConstructor
public class AuthenticatedInterceptor {

    private final AuthHelper authHelper;

    @Around("@annotation(com.meetry.backend.aspect.annotation.Authenticated)")
    public Object authenticate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        return proceedingJoinPoint.proceed();
    }

}

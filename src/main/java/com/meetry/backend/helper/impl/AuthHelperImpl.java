package com.meetry.backend.helper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthHelperImpl implements AuthHelper {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String SESSION_COOKIE_NAME = "meetry-session";
    private static final String REDIS_COOKIE_KEY = "meetry-session-%s";
    private static final int SEVEN_DAYS_IN_SECONDS = 7 * 24 * 60 * 60;

    @Override
    @SneakyThrows
    public Session getSessionFromCookie(HttpServletRequest httpServletRequest) {

        String sessionCookieValue = Optional.ofNullable(Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .collect(Collectors.toList())
                .get(0)
                .getValue())
                .orElseThrow(UnauthorizedException::new);

        String decodedSessionString = new String(Base64.getDecoder().decode(sessionCookieValue), StandardCharsets.UTF_8);

        return objectMapper.readValue(decodedSessionString, Session.class);
    }

    @Override
    public void setSessionCookie(HttpServletResponse httpServletResponse, String id, Role role) {

        Session session = Session.builder()
            .id(id)
            .role(role)
            .build();

        String encodedSession = getEncodedSession(session);

        String redisKey = String.format(REDIS_COOKIE_KEY, encodedSession);
        redisTemplate.opsForValue().set(redisKey, encodedSession);
        redisTemplate.expire(redisKey, 7, TimeUnit.DAYS);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, encodedSession);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(SEVEN_DAYS_IN_SECONDS);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        httpServletResponse.addCookie(cookie);
    }

    @SneakyThrows
    private String getEncodedSession(Session session){
        String sessionAsString = objectMapper.writeValueAsString(session);
        return Base64.getEncoder().encodeToString(sessionAsString.getBytes());
    }

}

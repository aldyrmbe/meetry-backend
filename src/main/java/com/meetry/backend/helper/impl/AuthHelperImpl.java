package com.meetry.backend.helper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.web.exception.UnauthorizedException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AuthHelperImpl implements AuthHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SESSION_COOKIE_NAME = "meetry-session";
    private static final String REDIS_COOKIE_KEY = "meetry-session-%s";
    private static final int SEVEN_DAYS_IN_SECONDS = 7 * 24 * 60 * 60;

    @Value("${cookie.domain}")
    private String domain;

    @Override
    @SneakyThrows
    public Session getSessionFromCookie(HttpServletRequest httpServletRequest) {

        String sessionCookieValue = getSessionValueFromCookie(httpServletRequest);
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

        Cookie cookie = constructCookie(encodedSession, SEVEN_DAYS_IN_SECONDS);
        httpServletResponse.addCookie(cookie);
    }

    @Override
    public void authenticate(HttpServletRequest httpServletRequest, Role authorizedRole) {

        Role requestedRole = getSessionFromCookie(httpServletRequest).getRole();
        String sessionCookieValue = getSessionValueFromCookie(httpServletRequest);
        String redisValue = getSessionFromRedis(sessionCookieValue);

        if(Role.PENELITI.equals(authorizedRole)){
            authenticatePeneliti(sessionCookieValue, redisValue, requestedRole);
        } else if(Role.MITRA.equals(authorizedRole)){
            authenticateMitra(sessionCookieValue, redisValue, requestedRole);
        } else if(Role.ERIC.equals(authorizedRole)){
            authenticateERIC(sessionCookieValue, redisValue, requestedRole);
        } else if(Role.ACCOUNT_OFFICER.equals(authorizedRole)){
            authenticateAccountOfficer(sessionCookieValue, redisValue, requestedRole);
        } else {
            authenticateAll(sessionCookieValue, redisValue);
        }
    }

    @Override
    public void destroyCandidateSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String sessionCookieValue = getSessionValueFromCookie(httpServletRequest);
        String redisKey = String.format(REDIS_COOKIE_KEY, sessionCookieValue);

        redisTemplate.delete(redisKey);

        Cookie cookie = constructCookie(sessionCookieValue, 0);
        httpServletResponse.addCookie(cookie);
    }

    private Cookie constructCookie(String value, int maxAge){

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setDomain(domain);
        return cookie;
    }

    private void authenticatePeneliti(String sessionCookieValue, String redisValue, Role requestedRole){

        if(!sessionCookieValue.equals(redisValue) || !Role.PENELITI.equals(requestedRole))
            throw new UnauthorizedException();
    }

    private void authenticateMitra(String sessionCookieValue, String redisValue, Role requestedRole){

        if(!sessionCookieValue.equals(redisValue) || !Role.MITRA.equals(requestedRole))
            throw new UnauthorizedException();
    }

    private void authenticateERIC(String sessionCookieValue, String redisValue, Role requestedRole){

        if(!sessionCookieValue.equals(redisValue) || !Role.ERIC.equals(requestedRole))
            throw new UnauthorizedException();
    }

    private void authenticateAccountOfficer(String sessionCookieValue, String redisValue, Role requestedRole){

        if(!sessionCookieValue.equals(redisValue) || !Role.ACCOUNT_OFFICER.equals(requestedRole))
            throw new UnauthorizedException();
    }

    private void authenticateAll(String sessionCookieValue, String redisValue){

        if(!sessionCookieValue.equals(redisValue))
            throw new UnauthorizedException();
    }

    @SneakyThrows
    private String getEncodedSession(Session session){

        String sessionAsString = objectMapper.writeValueAsString(session);
        return Base64.getEncoder().encodeToString(sessionAsString.getBytes());
    }

    private String getSessionFromRedis(String sessionCookieValue) {

        String redisKey = String.format(REDIS_COOKIE_KEY, sessionCookieValue);
        return Optional.ofNullable(redisTemplate.opsForValue()
                .get(redisKey))
            .orElseThrow(UnauthorizedException::new);
    }

    private String getSessionValueFromCookie(HttpServletRequest httpServletRequest){

        Cookie[] cookies = Optional.ofNullable(httpServletRequest.getCookies()).orElseThrow(UnauthorizedException::new);
        List<Cookie> sessionCookieList = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .collect(Collectors.toList());
        if (sessionCookieList.isEmpty()) throw new UnauthorizedException();
        return sessionCookieList.get(0).getValue();
    }

}

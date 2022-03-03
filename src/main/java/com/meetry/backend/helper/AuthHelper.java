package com.meetry.backend.helper;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthHelper {
    Session getSessionFromCookie(HttpServletRequest httpServletRequest);
    void setSessionCookie(HttpServletResponse httpServletResponse, String id, Role role);
}

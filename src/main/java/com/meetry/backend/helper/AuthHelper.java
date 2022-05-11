package com.meetry.backend.helper;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.proyek.Proyek;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AuthHelper {
    Session getSessionFromCookie(HttpServletRequest httpServletRequest);
    void setSessionCookie(HttpServletResponse httpServletResponse, String id, Role role);
    void authenticate(HttpServletRequest httpServletRequest, List<Role> authorizedRole);
    void destroyCandidateSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    void checkAccountOfficerAuthorization(Session session, Proyek proyek);
    void checkUserAuthorizationForLogbooks(Session session, String proyekId, String subFolderId);
}

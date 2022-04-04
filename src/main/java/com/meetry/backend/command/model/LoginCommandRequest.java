package com.meetry.backend.command.model;

import com.meetry.backend.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommandRequest {
    private String email;
    private String password;
    private HttpServletResponse httpServletResponse;
}


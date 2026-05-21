package com.meetry.backend.command.model;

import com.meetry.backend.entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogOutCommandRequest {
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
}

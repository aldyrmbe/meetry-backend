package com.meetry.backend.command.impl;

import com.meetry.backend.command.LogOutCommand;
import com.meetry.backend.command.model.LogOutCommandRequest;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogOutCommandImpl implements LogOutCommand {

    private AuthHelper authHelper;

    @Override
    public BaseResponse execute(LogOutCommandRequest commandRequest) {
        authHelper.destroyCandidateSession(commandRequest.getHttpServletRequest(), commandRequest.getHttpServletResponse());

        return BaseResponse.builder()
            .code(200)
            .status("OK")
            .message("Logout success.")
            .build();
    }
}

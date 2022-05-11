package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetCurrentLoggedInUserCommand;
import com.meetry.backend.command.model.GetCurrentLoggedInUserCommandRequest;
import com.meetry.backend.entity.Session;
import com.meetry.backend.web.model.response.BaseResponse;
import com.meetry.backend.web.model.response.UserWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetCurrentLoggedInUserCommandImpl implements GetCurrentLoggedInUserCommand {

    @Override
    public UserWebResponse execute(GetCurrentLoggedInUserCommandRequest commandRequest) {
        Session session = commandRequest.getSession();
        return UserWebResponse.builder()
            .id(session.getId())
            .role(session.getRole())
            .build();
    }
}

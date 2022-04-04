package com.meetry.backend.command;

import com.meetry.backend.command.model.GetCurrentLoggedInUserCommandRequest;
import com.meetry.backend.web.model.response.UserWebResponse;

public interface GetCurrentLoggedInUserCommand extends Command<GetCurrentLoggedInUserCommandRequest, UserWebResponse> {
}

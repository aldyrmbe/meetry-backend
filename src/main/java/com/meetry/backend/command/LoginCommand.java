package com.meetry.backend.command;

import com.meetry.backend.command.model.LoginCommandRequest;
import com.meetry.backend.web.model.response.LoginWebResponse;

public interface LoginCommand extends Command<LoginCommandRequest, LoginWebResponse> {
}

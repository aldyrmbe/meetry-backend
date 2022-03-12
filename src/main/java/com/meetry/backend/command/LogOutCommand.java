package com.meetry.backend.command;

import com.meetry.backend.command.model.LogOutCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface LogOutCommand extends Command<LogOutCommandRequest, BaseResponse> {
}

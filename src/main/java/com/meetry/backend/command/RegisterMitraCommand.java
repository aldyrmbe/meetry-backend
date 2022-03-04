package com.meetry.backend.command;

import com.meetry.backend.command.model.RegisterMitraCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface RegisterMitraCommand extends Command<RegisterMitraCommandRequest, BaseResponse> {
}

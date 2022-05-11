package com.meetry.backend.command;

import com.meetry.backend.command.model.GetMitraProfileCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetMitraProfileWebResponse;

public interface GetMitraProfileCommand extends Command<GetMitraProfileCommandRequest, DefaultResponse<GetMitraProfileWebResponse>> {
}

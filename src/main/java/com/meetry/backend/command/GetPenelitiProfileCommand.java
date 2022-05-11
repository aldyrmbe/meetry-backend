package com.meetry.backend.command;

import com.meetry.backend.command.model.GetPenelitiProfileCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetPenelitiProfileWebResponse;

public interface GetPenelitiProfileCommand
    extends Command<GetPenelitiProfileCommandRequest, DefaultResponse<GetPenelitiProfileWebResponse>> {
}

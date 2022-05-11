package com.meetry.backend.command;

import com.meetry.backend.command.model.GetLogbooksCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetLogbooksWebResponse;

public interface GetLogbooksCommand
    extends Command<GetLogbooksCommandRequest, DefaultResponse<GetLogbooksWebResponse>> {
}

package com.meetry.backend.command;

import com.meetry.backend.command.model.GetLogbookByIdCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetLogbookByIdWebResponse;

public interface GetLogbookByIdCommand extends Command<GetLogbookByIdCommandRequest, DefaultResponse<GetLogbookByIdWebResponse>> {
}

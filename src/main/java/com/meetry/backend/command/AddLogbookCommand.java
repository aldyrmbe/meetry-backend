package com.meetry.backend.command;

import com.meetry.backend.command.model.AddLogbookCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface AddLogbookCommand extends Command<AddLogbookCommandRequest, BaseResponse> {
}

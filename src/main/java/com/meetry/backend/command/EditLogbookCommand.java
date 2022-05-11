package com.meetry.backend.command;

import com.meetry.backend.command.model.EditLogbookCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface EditLogbookCommand extends Command<EditLogbookCommandRequest, BaseResponse> {
}

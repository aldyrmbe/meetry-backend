package com.meetry.backend.command;

import com.meetry.backend.command.model.DeleteLogbookCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface DeleteLogbookCommand extends Command<DeleteLogbookCommandRequest, BaseResponse> {
}

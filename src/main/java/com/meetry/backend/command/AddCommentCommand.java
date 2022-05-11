package com.meetry.backend.command;

import com.meetry.backend.command.model.AddCommentCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface AddCommentCommand extends Command<AddCommentCommandRequest, BaseResponse> {
}

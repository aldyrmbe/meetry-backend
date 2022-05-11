package com.meetry.backend.command;

import com.meetry.backend.command.model.DeleteSubFolderCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface DeleteSubFolderCommand extends Command<DeleteSubFolderCommandRequest, BaseResponse> {
}

package com.meetry.backend.command;

import com.meetry.backend.command.model.GetSubFoldersCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetSubFoldersWebResponse;

public interface GetSubFoldersCommand extends Command<GetSubFoldersCommandRequest, DefaultResponse<GetSubFoldersWebResponse>> {
}

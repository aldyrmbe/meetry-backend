package com.meetry.backend.command;

import com.meetry.backend.command.model.GetLogbookCommentsCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetLogbookCommentsWebResponse;

import java.util.List;

public interface GetLogbookCommentsCommand
    extends Command<GetLogbookCommentsCommandRequest, DefaultResponse<List<GetLogbookCommentsWebResponse>>> {
}

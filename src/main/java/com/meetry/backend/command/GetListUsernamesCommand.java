package com.meetry.backend.command;

import com.meetry.backend.command.model.GetListUsernamesCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetListUsernamesWebResponse;

import java.util.List;

public interface GetListUsernamesCommand
    extends Command<GetListUsernamesCommandRequest, DefaultResponse<List<GetListUsernamesWebResponse>>> {
}

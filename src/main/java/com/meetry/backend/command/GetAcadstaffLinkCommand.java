package com.meetry.backend.command;

import com.meetry.backend.command.model.GetAcadstaffLinkCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;

public interface GetAcadstaffLinkCommand extends Command<GetAcadstaffLinkCommandRequest, DefaultResponse<String>> {
}

package com.meetry.backend.command;

import com.meetry.backend.command.model.GetListAccountOfficersCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetAccountOfficerListWebResponse;

public interface GetListAccountOfficersCommand
    extends Command<GetListAccountOfficersCommandRequest, DefaultResponse<GetAccountOfficerListWebResponse>> {
}

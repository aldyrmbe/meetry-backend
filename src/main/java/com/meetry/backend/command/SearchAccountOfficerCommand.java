package com.meetry.backend.command;

import com.meetry.backend.command.model.SearchAccountOfficerCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.SearchAccountOfficerWebResponse;

public interface SearchAccountOfficerCommand
    extends Command<SearchAccountOfficerCommandRequest, DefaultResponse<SearchAccountOfficerWebResponse>> {
}

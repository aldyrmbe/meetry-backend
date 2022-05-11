package com.meetry.backend.command;

import com.meetry.backend.command.model.SearchMitraCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.SearchMitraWebResponse;

public interface SearchMitraCommand
    extends Command<SearchMitraCommandRequest, DefaultResponse<SearchMitraWebResponse>> {
}

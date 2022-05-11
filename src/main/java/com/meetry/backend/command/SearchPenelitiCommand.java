package com.meetry.backend.command;

import com.meetry.backend.command.model.SearchPenelitiCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.SearchPenelitiWebResponse;

public interface SearchPenelitiCommand
    extends Command<SearchPenelitiCommandRequest, DefaultResponse<SearchPenelitiWebResponse>> {
}

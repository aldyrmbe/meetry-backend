package com.meetry.backend.command;

import com.meetry.backend.command.model.GetListProyekCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.ListProyekWebResponse;

public interface GetListProyekCommand
    extends Command<GetListProyekCommandRequest, DefaultResponse<ListProyekWebResponse>> {
}

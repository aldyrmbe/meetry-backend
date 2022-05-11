package com.meetry.backend.command;

import com.meetry.backend.command.model.GetListProyekOnRequestCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetListProyekOnRequestWebRequest;

public interface GetListProyekOnRequestCommand extends Command<GetListProyekOnRequestCommandRequest, DefaultResponse<GetListProyekOnRequestWebRequest>> {
}

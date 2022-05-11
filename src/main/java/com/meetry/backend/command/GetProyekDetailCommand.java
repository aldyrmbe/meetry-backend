package com.meetry.backend.command;

import com.meetry.backend.command.model.GetProyekDetailCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.ProyekDetailWebResponse;

public interface GetProyekDetailCommand extends Command<GetProyekDetailCommandRequest, DefaultResponse<ProyekDetailWebResponse>> {
}

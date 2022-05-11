package com.meetry.backend.command;

import com.meetry.backend.command.model.GetIndonesianUniversitiesCommandRequest;
import com.meetry.backend.web.model.response.BaseResponse;

public interface GetIndonesianUniversitiesCommand
    extends Command<GetIndonesianUniversitiesCommandRequest, BaseResponse> {
}

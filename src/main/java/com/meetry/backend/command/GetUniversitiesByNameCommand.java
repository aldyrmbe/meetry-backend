package com.meetry.backend.command;

import com.meetry.backend.command.model.GetUniversitiesByNameCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetUniversitiesByNameWebResponse;

import java.util.List;

public interface GetUniversitiesByNameCommand
    extends Command<GetUniversitiesByNameCommandRequest, DefaultResponse<List<GetUniversitiesByNameWebResponse>>> {
}

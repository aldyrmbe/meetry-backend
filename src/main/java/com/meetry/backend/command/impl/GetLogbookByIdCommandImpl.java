package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetLogbookByIdCommand;
import com.meetry.backend.command.model.GetLogbookByIdCommandRequest;
import com.meetry.backend.entity.logbook.Logbook;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.LogbookRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetLogbookByIdWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetLogbookByIdCommandImpl implements GetLogbookByIdCommand {

    private final LogbookRepository logbookRepository;

    private final ProyekHelper proyekHelper;

    private final AuthHelper authHelper;

    @Override
    public DefaultResponse<GetLogbookByIdWebResponse> execute(GetLogbookByIdCommandRequest commandRequest) {

        Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
        proyekHelper.verifyProyekStatusForLogbookOperations(proyek);
        authHelper.checkAccountOfficerAuthorization(commandRequest.getSession(), proyek);
        Logbook logbook = getLogbookById(commandRequest);

        return DefaultResponse.<GetLogbookByIdWebResponse>builder()
            .code(200)
            .status("OK")
            .data(toGetLogbookByIdWebResponse(logbook))
            .build();
    }

    private Logbook getLogbookById(GetLogbookByIdCommandRequest commandRequest){
        return logbookRepository.findById(commandRequest.getLogbookId())
            .orElseThrow(() -> new BaseException("Logbook tidak ditemukan."));
    }

    private GetLogbookByIdWebResponse toGetLogbookByIdWebResponse(Logbook logbook){
        return GetLogbookByIdWebResponse.builder()
            .id(logbook.getId())
            .judul(logbook.getTitle())
            .deskripsi(logbook.getContent())
            .waktu(logbook.getActivityTime())
            .tags(logbook.getTags())
            .build();
    }
}

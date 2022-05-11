package com.meetry.backend.command.impl;

import com.meetry.backend.command.EditLogbookCommand;
import com.meetry.backend.command.model.EditLogbookCommandRequest;
import com.meetry.backend.entity.logbook.Logbook;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.LogbookRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EditLogbookCommandImpl implements EditLogbookCommand {

    private final LogbookRepository logbookRepository;

    private final ProyekHelper proyekHelper;

    private final AuthHelper authHelper;

    @Override
    public BaseResponse execute(EditLogbookCommandRequest commandRequest) {

        Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
        proyekHelper.verifyProyekStatusForLogbookOperations(proyek);
        authHelper.checkAccountOfficerAuthorization(commandRequest.getSession(), proyek);

        Logbook logbook = logbookRepository.findById(commandRequest.getLogbookId())
            .orElseThrow(() -> new BaseException("Logbook tidak ditemukan"));

        logbook.setTitle(commandRequest.getJudul());
        logbook.setActivityTime(commandRequest.getWaktu());
        logbook.setContent(commandRequest.getDeskripsi());
        logbook.setTags(commandRequest.getTags());

        logbookRepository.save(logbook);

        return BaseResponse.builder()
            .code(200)
            .status("OK")
            .message("Logbook berhasil diedit.")
            .build();
    }
}

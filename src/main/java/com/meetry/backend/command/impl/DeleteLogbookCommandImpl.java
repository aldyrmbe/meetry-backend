package com.meetry.backend.command.impl;

import com.meetry.backend.command.DeleteLogbookCommand;
import com.meetry.backend.command.model.DeleteLogbookCommandRequest;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.LogbookRepository;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteLogbookCommandImpl implements DeleteLogbookCommand {

    private final LogbookRepository logbookRepository;

    private final ProyekHelper proyekHelper;

    private final AuthHelper authHelper;

    @Override
    public BaseResponse execute(DeleteLogbookCommandRequest commandRequest) {

        Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
        proyekHelper.verifyProyekStatusForLogbookOperations(proyek);
        authHelper.checkAccountOfficerAuthorization(commandRequest.getSession(), proyek);
        logbookRepository.deleteById(commandRequest.getLogbookId());

        return BaseResponse.builder()
            .code(200)
            .status("OK")
            .message("Logbook berhasil dihapus.")
            .build();
    }


}

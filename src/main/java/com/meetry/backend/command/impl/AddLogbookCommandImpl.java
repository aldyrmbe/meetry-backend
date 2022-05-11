package com.meetry.backend.command.impl;

import com.meetry.backend.command.AddLogbookCommand;
import com.meetry.backend.command.model.AddLogbookCommandRequest;
import com.meetry.backend.entity.logbook.Logbook;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.LogbookRepository;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class AddLogbookCommandImpl implements AddLogbookCommand {

  private final LogbookRepository logbookRepository;

  private final AuthHelper authHelper;

  private final ProyekHelper proyekHelper;

  @Override
  public BaseResponse execute(AddLogbookCommandRequest commandRequest) {

    Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
    proyekHelper.verifyProyekStatusForLogbookOperations(proyek);
    authHelper.checkAccountOfficerAuthorization(commandRequest.getSession(), proyek);
    constructLogbook(commandRequest);
    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Logbook berhasil ditambahkan.")
        .build();
  }

  private void constructLogbook(AddLogbookCommandRequest commandRequest) {

    Logbook logbook = Logbook.builder()
        .createdAt(Instant.now()
            .toEpochMilli())
        .senderId(commandRequest.getSession().getId())
        .subFolderId(commandRequest.getSubFolderId())
        .title(commandRequest.getJudul())
        .activityTime(commandRequest.getWaktu())
        .content(commandRequest.getDeskripsi())
        .tags(commandRequest.getTags())
        .build();

    logbookRepository.save(logbook);
  }
}

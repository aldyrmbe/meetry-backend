package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetProyekFilesCommand;
import com.meetry.backend.command.model.GetProyekFilesCommandRequest;
import com.meetry.backend.entity.proyek.File;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetProyekFilesWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetProyekFilesCommandImpl implements GetProyekFilesCommand {

  private final AuthHelper authHelper;

  private final ProyekHelper proyekHelper;

  @Override
  public DefaultResponse<List<GetProyekFilesWebResponse>> execute(GetProyekFilesCommandRequest commandRequest) {

    return DefaultResponse.<List<GetProyekFilesWebResponse>>builder()
        .code(200)
        .status("OK")
        .data(toListGetProyekFilesWebResponse(commandRequest))
        .build();
  }

  private List<File> getFiles(GetProyekFilesCommandRequest commandRequest) {

    authHelper.checkUserAuthorizationForProyekFiles(commandRequest.getSession(), commandRequest.getProyekId());

    return proyekHelper.findProyekById(commandRequest.getProyekId())
        .getFiles();
  }

  private List<GetProyekFilesWebResponse> toListGetProyekFilesWebResponse(GetProyekFilesCommandRequest commandRequest) {

    List<File> files = getFiles(commandRequest);
    if(Objects.isNull(files)){
      return null;
    }
    return getFiles(commandRequest).stream()
        .map(file -> GetProyekFilesWebResponse.builder()
            .name(file.getName())
            .url(file.getUrl())
            .build())
        .collect(Collectors.toList());
  }
}

package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetAcadstaffLinkCommand;
import com.meetry.backend.command.model.GetAcadstaffLinkCommandRequest;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetAcadstaffLinkCommandImpl implements GetAcadstaffLinkCommand {

  private final PenelitiRepository penelitiRepository;

  @Override
  public DefaultResponse<String> execute(GetAcadstaffLinkCommandRequest commandRequest) {

    return DefaultResponse.<String>builder()
        .code(200)
        .status("OK")
        .data(getAcadStaffLink(commandRequest.getSession()
            .getId()))
        .build();
  }

  private String getAcadStaffLink(String id) {

    return penelitiRepository.findById(id)
        .orElseThrow(() -> new BaseException("Peneliti tidak ditemukan."))
        .getAcadstaffLink();
  }
}

package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetListUsernamesCommand;
import com.meetry.backend.command.model.GetListUsernamesCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetListUsernamesWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetListUsernamesCommandImpl implements GetListUsernamesCommand {

  private final PenelitiRepository penelitiRepository;

  private final MitraRepository mitraRepository;

  private final AccountOfficerRepository accountOfficerRepository;

  @Override
  public DefaultResponse<List<GetListUsernamesWebResponse>> execute(GetListUsernamesCommandRequest commandRequest) {

    verifyRoleParameter(commandRequest.getRole());
    List<GetListUsernamesWebResponse> usernames = getUsernames(commandRequest);

    return DefaultResponse.<List<GetListUsernamesWebResponse>>builder()
        .code(200)
        .status("OK")
        .data(usernames)
        .build();
  }

  private void verifyRoleParameter(Role role) {

    List<Role> validRole = Arrays.asList(Role.PENELITI, Role.MITRA, Role.ACCOUNT_OFFICER);
    if (!validRole.contains(role))
      throw new BaseException("Parameter role tidak sesuai");
  }

  private List<GetListUsernamesWebResponse> getUsernames(GetListUsernamesCommandRequest commandRequest) {

    if (Role.PENELITI.equals(commandRequest.getRole())) {
      return penelitiRepository.getPenelitiByName(commandRequest.getQuery())
          .stream()
          .map(peneliti -> GetListUsernamesWebResponse.builder()
              .label(peneliti.getNamaLengkap())
              .value(peneliti.getId())
              .build())
          .collect(Collectors.toList());
    }

    if (Role.MITRA.equals(commandRequest.getRole())) {
      return mitraRepository.getMitraByName(commandRequest.getQuery())
          .stream()
          .map(mitra -> GetListUsernamesWebResponse.builder()
              .label(mitra.getNamaPerusahaan())
              .value(mitra.getId())
              .build())
          .collect(Collectors.toList());
    }

    return accountOfficerRepository.getAccountOfficerByName(commandRequest.getQuery())
        .stream()
        .map(accountOfficer -> GetListUsernamesWebResponse.builder()
            .label(accountOfficer.getName())
            .value(accountOfficer.getId())
            .build())
        .collect(Collectors.toList());
  }

}

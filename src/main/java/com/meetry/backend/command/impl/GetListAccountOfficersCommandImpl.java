package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetListAccountOfficersCommand;
import com.meetry.backend.command.model.GetListAccountOfficersCommandRequest;
import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.web.model.response.AccountOfficerDetailsWebResponse;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetAccountOfficerListWebResponse;
import com.meetry.backend.web.model.response.PaginationData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetListAccountOfficersCommandImpl implements GetListAccountOfficersCommand {

  private final AccountOfficerRepository accountOfficerRepository;

  @Override
  public DefaultResponse<GetAccountOfficerListWebResponse> execute(
      GetListAccountOfficersCommandRequest commandRequest) {

    Page<AccountOfficer> accountOfficerPage = getAllAccountOfficer(commandRequest);

    return DefaultResponse.<GetAccountOfficerListWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toGetAccountOfficerListWebResponse(accountOfficerPage))
        .build();
  }

  private Page<AccountOfficer> getAllAccountOfficer(GetListAccountOfficersCommandRequest commandRequest) {

    return accountOfficerRepository.findAll(PageRequest.of(commandRequest.getPage(), 5, Sort.by("name")));
  }

  private GetAccountOfficerListWebResponse toGetAccountOfficerListWebResponse(
      Page<AccountOfficer> accountOfficerPage) {

    PaginationData paginationData = PaginationData.builder()
        .currentPage(accountOfficerPage.getPageable()
            .getPageNumber() + 1)
        .totalPage(accountOfficerPage.getTotalPages())
        .build();

    List<AccountOfficerDetailsWebResponse> accountOfficers = accountOfficerPage.getContent().stream()
        .map(accountOfficer -> AccountOfficerDetailsWebResponse.builder()
            .nama(accountOfficer.getName())
            .email(accountOfficer.getEmail())
            .profilePhoto(accountOfficer.getProfilePhoto())
            .build())
        .collect(Collectors.toList());

    return GetAccountOfficerListWebResponse.builder()
        .paginationData(paginationData)
        .accountOfficerList(accountOfficers)
        .build();
  }
}

package com.meetry.backend.command.impl;

import com.meetry.backend.command.SearchAccountOfficerCommand;
import com.meetry.backend.command.model.SearchAccountOfficerCommandRequest;
import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.PaginationData;
import com.meetry.backend.web.model.response.SearchAccountOfficerWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SearchAccountOfficerCommandImpl implements SearchAccountOfficerCommand {

  private final AccountOfficerRepository accountOfficerRepository;

  @Override
  public DefaultResponse<SearchAccountOfficerWebResponse> execute(SearchAccountOfficerCommandRequest commandRequest) {

    Page<AccountOfficer> accountOfficerPage = getAccountOfficerPage(commandRequest);

    return DefaultResponse.<SearchAccountOfficerWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toSearchAccountOfficerWebResponse(accountOfficerPage))
        .build();
  }

  private Page<AccountOfficer> getAccountOfficerPage(SearchAccountOfficerCommandRequest commandRequest) {

    return accountOfficerRepository.searchAccountOfficerByName(commandRequest.getSearchQuery(),
        commandRequest.getPage());
  }

  private SearchAccountOfficerWebResponse toSearchAccountOfficerWebResponse(Page<AccountOfficer> accountOfficerPage) {

    PaginationData paginationData = PaginationData.builder()
        .currentPage(accountOfficerPage.getPageable()
            .getPageNumber() + 1)
        .totalPage(accountOfficerPage.getTotalPages())
        .build();

    List<SearchAccountOfficerWebResponse.AccountOfficerDetail> accountOfficerList = accountOfficerPage.getContent()
        .stream()
        .map(accountOfficer -> SearchAccountOfficerWebResponse.AccountOfficerDetail.builder()
            .id(accountOfficer.getId())
            .fotoProfil(accountOfficer.getProfilePhoto())
            .nama(accountOfficer.getName())
            .email(accountOfficer.getEmail())
            .build())
        .collect(Collectors.toList());

    return SearchAccountOfficerWebResponse.builder()
        .paginationData(paginationData)
        .accountOfficerList(accountOfficerList)
        .build();
  }
}

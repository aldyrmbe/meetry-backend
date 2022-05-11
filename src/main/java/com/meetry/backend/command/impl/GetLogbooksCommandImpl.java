package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetLogbooksCommand;
import com.meetry.backend.command.model.GetLogbooksCommandRequest;
import com.meetry.backend.entity.logbook.Logbook;
import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.entity.user.ERIC;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.repository.LogbookRepository;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.repository.user.ERICRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetLogbooksWebResponse;
import com.meetry.backend.web.model.response.PaginationData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetLogbooksCommandImpl implements GetLogbooksCommand {

  private final LogbookRepository logbookRepository;

  private final ERICRepository ericRepository;

  private final AccountOfficerRepository accountOfficerRepository;

  private final AuthHelper authHelper;

  @Override
  public DefaultResponse<GetLogbooksWebResponse> execute(GetLogbooksCommandRequest commandRequest) {

    authHelper.checkUserAuthorizationForLogbooks(commandRequest.getSession(), commandRequest.getProyekId(),
        commandRequest.getSubFolderId());

    Page<Logbook> logbookPage = getLogbooks(commandRequest);
    List<GetLogbooksWebResponse.LogbookData> logbooks = getLogbooksDetail(logbookPage.getContent());
    PaginationData paginationData = getPaginationData(logbookPage);

    return DefaultResponse.<GetLogbooksWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toGetLogbooksWebResponse(logbooks, paginationData))
        .build();
  }

  private Page<Logbook> getLogbooks(GetLogbooksCommandRequest commandRequest) {

    return logbookRepository.findAllBySubFolderIdOrderByCreatedAtDesc(commandRequest.getSubFolderId(),
        PageRequest.of(commandRequest.getPage(), 5));
  }

  private PaginationData getPaginationData(Page<Logbook> logbookPage) {

    return PaginationData.builder()
        .currentPage(logbookPage.getPageable()
            .getPageNumber() + 1)
        .totalPage(logbookPage.getTotalPages())
        .build();
  }

  private List<GetLogbooksWebResponse.LogbookData> getLogbooksDetail(List<Logbook> logbooks) {

    return logbooks.stream()
        .map(logbook -> GetLogbooksWebResponse.LogbookData.builder()
            .id(logbook.getId())
            .createdAt(logbook.getCreatedAt())
            .sender(getLogbookSender(logbook))
            .judul(logbook.getTitle())
            .deskripsi(logbook.getContent())
            .waktu(logbook.getActivityTime())
            .tags(logbook.getTags())
            .build())
        .collect(Collectors.toList());
  }

  private GetLogbooksWebResponse.LogbookData.Sender getLogbookSender(Logbook logbook) {

    Optional<ERIC> eric = ericRepository.findById(logbook.getSenderId());
    if (!eric.isPresent()) {
      AccountOfficer accountOfficer = accountOfficerRepository.findById(logbook.getSenderId())
          .orElseThrow(() -> new BaseException("Account officer tidak ditemukan"));

      return GetLogbooksWebResponse.LogbookData.Sender.builder()
          .nama(accountOfficer.getName())
          .fotoProfil(accountOfficer.getProfilePhoto())
          .build();
    }

    return GetLogbooksWebResponse.LogbookData.Sender.builder()
        .nama(eric.get()
            .getName())
        .fotoProfil(eric.get()
            .getProfilePhoto())
        .build();
  }

  private GetLogbooksWebResponse toGetLogbooksWebResponse(List<GetLogbooksWebResponse.LogbookData> logbookData,
      PaginationData paginationData) {

    return GetLogbooksWebResponse.builder()
        .paginationData(paginationData)
        .logbookData(logbookData)
        .build();
  }
}

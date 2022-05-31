package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetLogbookCommentsCommand;
import com.meetry.backend.command.model.GetLogbookCommentsCommandRequest;
import com.meetry.backend.entity.komentar.File;
import com.meetry.backend.entity.komentar.Komentar;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.repository.KomentarRepository;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetLogbookCommentsWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetLogbookCommentsCommandImpl implements GetLogbookCommentsCommand {

  private final KomentarRepository komentarRepository;

  private final AuthHelper authHelper;

  @Override
  public DefaultResponse<List<GetLogbookCommentsWebResponse>> execute(GetLogbookCommentsCommandRequest commandRequest) {

    authHelper.checkUserAuthorizationForLogbooks(commandRequest.getSession(), commandRequest.getProyekId(),
        commandRequest.getSubFolderId());

    List<Komentar> comments = getComments(commandRequest.getLogbookId());

    return DefaultResponse.<List<GetLogbookCommentsWebResponse>>builder()
        .code(200)
        .status("OK")
        .data(toListGetLogbookCommentsWebResponse(comments))
        .build();
  }

  private List<Komentar> getComments(String logbookId) {

    return komentarRepository.findAllByLogbookIdOrderByCreatedAtAsc(logbookId);
  }

  private List<GetLogbookCommentsWebResponse> toListGetLogbookCommentsWebResponse(List<Komentar> comments) {

    return comments.stream()
        .map(comment -> GetLogbookCommentsWebResponse.builder()
            .profilePhoto(comment.getProfilePhoto())
            .pengirim(comment.getSenderName())
            .waktu(comment.getCreatedAt())
            .isi(comment.getContent())
            .files(getFiles(comment.getFiles()))
            .build())
        .collect(Collectors.toList());
  }

  private List<GetLogbookCommentsWebResponse.File> getFiles(List<File> files){
    if(Objects.isNull(files)){
      return null;
    }

    return files.stream()
        .map(file -> GetLogbookCommentsWebResponse.File.builder()
            .fileName(file.getName())
            .fileUrl(file.getUrl())
            .build())
        .collect(Collectors.toList());
  }

}

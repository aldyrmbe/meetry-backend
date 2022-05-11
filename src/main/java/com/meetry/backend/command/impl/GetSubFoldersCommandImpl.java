package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetSubFoldersCommand;
import com.meetry.backend.command.model.GetSubFoldersCommandRequest;
import com.meetry.backend.entity.folder.Folder;
import com.meetry.backend.repository.FolderRepository;
import com.meetry.backend.repository.SubFolderRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetSubFoldersWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetSubFoldersCommandImpl implements GetSubFoldersCommand {

  private final FolderRepository folderRepository;

  private final SubFolderRepository subFolderRepository;

  @Override
  public DefaultResponse<GetSubFoldersWebResponse> execute(GetSubFoldersCommandRequest commandRequest) {

    return DefaultResponse.<GetSubFoldersWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toGetSubFoldersWebResponse(commandRequest))
        .build();

  }

  private List<GetSubFoldersWebResponse.SubFolder> getSubFolders(GetSubFoldersCommandRequest commandRequest) {

    return subFolderRepository.findAllByFolderId(commandRequest.getFolderId()).stream()
        .map(subFolder -> GetSubFoldersWebResponse.SubFolder.builder()
            .id(subFolder.getId())
            .namaSubFolder(subFolder.getSubFolderName())
            .build())
        .collect(Collectors.toList());
  }

  private GetSubFoldersWebResponse toGetSubFoldersWebResponse(GetSubFoldersCommandRequest commandRequest) {

    Folder folder = folderRepository.findById(commandRequest.getFolderId())
        .orElseThrow(() -> new BaseException("Folder tidak ditemukan"));

    return GetSubFoldersWebResponse.builder()
        .folderName(folder.getFolderName())
        .subFolders(getSubFolders(commandRequest))
        .build();
  }
}

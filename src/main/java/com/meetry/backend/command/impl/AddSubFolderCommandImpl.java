package com.meetry.backend.command.impl;

import com.meetry.backend.command.AddSubFolderCommand;
import com.meetry.backend.command.model.AddSubFolderCommandRequest;
import com.meetry.backend.entity.subfolder.SubFolder;
import com.meetry.backend.helper.DirectoryHelper;
import com.meetry.backend.repository.SubFolderRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class AddSubFolderCommandImpl implements AddSubFolderCommand {

  private final SubFolderRepository subFolderRepository;

  private final DirectoryHelper directoryHelper;

  @Override
  public BaseResponse execute(AddSubFolderCommandRequest commandRequest) {

    createSubFolder(commandRequest);

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Subfolder berhasil ditambahkan.")
        .build();
  }

  private void createSubFolder(AddSubFolderCommandRequest commandRequest){

    directoryHelper.checkSubFolderNameAvailability(commandRequest.getFolderId(), commandRequest.getSubFolderName());

    SubFolder subFolder = SubFolder.builder()
        .folderId(commandRequest.getFolderId())
        .subFolderName(commandRequest.getSubFolderName())
        .build();

    subFolderRepository.save(subFolder);
  }
}

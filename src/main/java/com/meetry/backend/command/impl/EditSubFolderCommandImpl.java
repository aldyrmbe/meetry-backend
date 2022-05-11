package com.meetry.backend.command.impl;

import com.meetry.backend.command.EditSubFolderCommand;
import com.meetry.backend.command.model.EditSubFolderCommandRequest;
import com.meetry.backend.entity.subfolder.SubFolder;
import com.meetry.backend.helper.DirectoryHelper;
import com.meetry.backend.repository.SubFolderRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EditSubFolderCommandImpl implements EditSubFolderCommand {

    private final SubFolderRepository subFolderRepository;

    private final DirectoryHelper directoryHelper;

    @Override
    public BaseResponse execute(EditSubFolderCommandRequest commandRequest) {

        directoryHelper.checkSubFolderNameAvailability(commandRequest.getFolderId(), commandRequest.getSubFolderName());

        SubFolder subFolder = subFolderRepository.findById(commandRequest.getSubFolderId())
            .orElseThrow(() -> new BaseException("Subfolder tidak ditemukan."));

        subFolder.setSubFolderName(commandRequest.getSubFolderName());
        subFolderRepository.save(subFolder);

        return BaseResponse.builder()
            .code(200)
            .status("OK")
            .message("Subfolder berhasil diedit.")
            .build();
    }
}

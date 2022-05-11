package com.meetry.backend.command.impl;

import com.meetry.backend.command.DeleteSubFolderCommand;
import com.meetry.backend.command.model.DeleteSubFolderCommandRequest;
import com.meetry.backend.repository.SubFolderRepository;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteSubFolderCommandImpl implements DeleteSubFolderCommand {

    private final SubFolderRepository subFolderRepository;

    @Override
    public BaseResponse execute(DeleteSubFolderCommandRequest commandRequest) {

        subFolderRepository.deleteById(commandRequest.getSubFolderId());

        return BaseResponse.builder()
            .code(200)
            .status("OK")
            .message("Subfolder berhasil dihapus.")
            .build();
    }
}

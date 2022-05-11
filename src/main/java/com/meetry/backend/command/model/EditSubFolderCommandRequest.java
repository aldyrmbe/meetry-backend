package com.meetry.backend.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditSubFolderCommandRequest {
    private String folderId;
    private String subFolderId;
    private String subFolderName;
}

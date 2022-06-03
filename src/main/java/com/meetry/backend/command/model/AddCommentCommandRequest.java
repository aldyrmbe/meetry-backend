package com.meetry.backend.command.model;

import com.meetry.backend.entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentCommandRequest {
    private Session session;
    private String proyekId;
    private String folderId;
    private String subFolderId;
    private String subFolderName;
    private String logbookId;
    private String content;
    private MultipartFile[] files;
}

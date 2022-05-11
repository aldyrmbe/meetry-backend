package com.meetry.backend.command.model;

import com.meetry.backend.entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentCommandRequest {
    private Session session;
    private String proyekId;
    private String subFolderId;
    private String logbookId;
    private String content;
}

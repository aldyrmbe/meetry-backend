package com.meetry.backend.command.model;

import com.meetry.backend.entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddLogbookCommandRequest {
    private Session session;
    private String proyekId;
    private String subFolderId;
    private String judul;
    private Long waktu;
    private String deskripsi;
    private List<String> tags;
}

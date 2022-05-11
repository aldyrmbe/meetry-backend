package com.meetry.backend.command.model;

import com.meetry.backend.entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditLogbookCommandRequest {
    private Session session;
    private String proyekId;
    private String logbookId;
    private String judul;
    private Long waktu;
    private String deskripsi;
    private List<String> tags;
}

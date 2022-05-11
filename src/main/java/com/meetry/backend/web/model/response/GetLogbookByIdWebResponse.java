package com.meetry.backend.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetLogbookByIdWebResponse {
    private String id;
    private String judul;
    private Long waktu;
    private String deskripsi;
    private List<String> tags;
}

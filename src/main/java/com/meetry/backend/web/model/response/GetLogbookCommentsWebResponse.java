package com.meetry.backend.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLogbookCommentsWebResponse {
    private String profilePhoto;
    private String pengirim;
    private Long waktu;
    private String isi;
    private List<File> files;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class File {
        private String fileName;
        private String fileUrl;
    }
}

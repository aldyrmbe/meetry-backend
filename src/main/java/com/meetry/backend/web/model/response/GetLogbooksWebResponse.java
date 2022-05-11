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
public class GetLogbooksWebResponse {

    private PaginationData paginationData;
    private List<LogbookData> logbookData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogbookData {
        private String id;
        private Long createdAt;
        private Sender sender;
        private String judul;
        private Long waktu;
        private String deskripsi;
        private List<String> tags;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Sender {
            private String fotoProfil;
            private String nama;
        }
    }
}

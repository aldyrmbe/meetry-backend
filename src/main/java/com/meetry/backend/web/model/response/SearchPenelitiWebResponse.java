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
public class SearchPenelitiWebResponse {
    private PaginationData paginationData;
    private List<PenelitiDetail> penelitiList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PenelitiDetail {
        private String id;
        private String fotoProfil;
        private String nama;
        private String programStudi;
        private String profileUrl;
    }
}

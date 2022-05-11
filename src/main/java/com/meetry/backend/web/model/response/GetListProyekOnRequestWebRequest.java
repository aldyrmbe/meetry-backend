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
public class GetListProyekOnRequestWebRequest {
    private PaginationData paginationData;
    private List<ProyekDetail> proyekList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProyekDetail {
        private String id;
        private String pemohon;
        private String fotoProfil;
        private String judul;
        private String bidang;
    }
}

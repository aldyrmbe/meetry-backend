package com.meetry.backend.web.model.response;

import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListProyekWebResponse {
    PaginationData paginationData;
    List<ProyekData> proyekData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProyekData {
        private String id;
        private Role pemohon;
        private String judul;
        private String partisipan;
        private StatusProyek status;
    }
}

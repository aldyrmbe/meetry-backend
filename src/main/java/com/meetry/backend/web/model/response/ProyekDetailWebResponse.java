package com.meetry.backend.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ProyekDetailWebResponse {

    private StatusProyek status;
    private Role pemohon;
    private OverviewProyek overviewProyek;
    private List<KebutuhanProyek> kebutuhanProyek;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Folder> folders;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OverviewProyek {

        private String judul;
        private Partisipan partisipan;
        private String periode;
        private String bidang;
        private String latarBelakang;
        private String tujuan;
        private String sasaran;
        private String output;
        private String kebermanfaatanProduk;
        private String indikatorKesuksesan;
        private String tingkatKesiapan;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<Pendukung> linkPendukung;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<Pendukung> dokumenPendukung;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String whatsappGroupLink;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Partisipan {
            private List<DetailPartisipan> mitra;
            private List<DetailPartisipan> peneliti;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private DetailPartisipan accountOfficer;

            @Data
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            public static class DetailPartisipan {
                private String id;
                private String nama;
                private String fotoProfil;
                private String profilePageUrl;
            }
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Pendukung {
            private String nama;
            private String value;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KebutuhanProyek {

        private String kebutuhanProyek;
        private String bentukKolaborasi;
        private String penjelasanTambahan;
        private String partisipan;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Folder {
        private String id;
        private String namaFolder;
    }
}

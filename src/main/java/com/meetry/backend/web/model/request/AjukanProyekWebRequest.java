package com.meetry.backend.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AjukanProyekWebRequest {
    private String judul;
    private Long periodeMulai;
    private Long periodeSelesai;
    private String bidang;
    private String latarBelakang;
    private String tujuan;
    private String sasaran;
    private String output;
    private String kebermanfaatanProduk;
    private String indikatorKesuksesan;
    private String tingkatKesiapan;
    private List<String> linkPendukung;
    private List<KebutuhanProyek> kebutuhanProyek;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KebutuhanProyek {
        private String kebutuhanProyek;
        private String bentukKolaborasi;
        private String penjelasanTambahan;
    }
}

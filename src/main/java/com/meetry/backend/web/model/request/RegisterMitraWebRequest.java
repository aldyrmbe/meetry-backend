package com.meetry.backend.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMitraWebRequest {
    private String namaPerusahaan;
    private String email;
    private String password;
    private String provinsi;
    private String kabupaten;
    private String alamatLengkap;
    private String bidangPerusahaan;
    private String jenisPerusahaan;
    private String nomorTelepon;
    private int tahunBerdiri;
    private int jumlahKaryawan;
    private String profilSingkat;
    private String website;
}

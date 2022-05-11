package com.meetry.backend.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPenelitiWebRequest {

    private String namaLengkap;
    private String email;
    private String password;
    private String NIDN;
    private String perguruanTinggi;
    private String programStudi;
    private String jenisKelamin;
    private String nomorKTP;
    private Long tanggalLahir;
    private String nomorTelepon;
    private String alamatLengkap;
    private String acadstaffLink;
}

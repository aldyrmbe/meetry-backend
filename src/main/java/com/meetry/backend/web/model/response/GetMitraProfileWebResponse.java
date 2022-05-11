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
public class GetMitraProfileWebResponse {
    private String namaPerusahaan;
    private String fotoProfil;
    private List<String> bidangPerusahaan;
    private String nomorTelepon;
    private String email;
    private String website;
    private String profilSingkat;
    private String jenisPerusahaan;
    private String alamat;
}

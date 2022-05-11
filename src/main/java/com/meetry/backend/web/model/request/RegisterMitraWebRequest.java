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
public class RegisterMitraWebRequest {
    private String namaPerusahaan;
    private String email;
    private String password;
    private String jenisPerusahaan;
    private List<String> bidangPerusahaan;
    private String nomorTelepon;
    private String alamat;
    private String profilSingkat;
    private String website;
}

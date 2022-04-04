package com.meetry.backend.entity.user;

import com.meetry.backend.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mitra")
public class Mitra {
    @Id
    private String id;

    @Field(value = "email")
    private String email;

    @Field(value = "password")
    private String password;

    @Field(value = "role")
    private Role role;

    @Field
    private String namaPerusahaan;

    @Field
    private String provinsi;

    @Field
    private String kabupaten;

    @Field
    private String alamatLengkap;

    @Field
    private List<String> bidangPerusahaan;

    @Field
    private String jenisPerusahaan;

    @Field
    private String nomorTelepon;

    @Field
    private int tahunBerdiri;

    @Field
    private int jumlahKaryawan;

    @Field
    private String profilSingkat;

    @Field
    private String website;

    @Field
    private String fotoProfil;
}

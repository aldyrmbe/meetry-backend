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

    private Role role;

    @Field
    private String namaPerusahaan;

    @Field
    private String email;

    @Field
    private String password;

    @Field
    private String jenisPerusahaan;

    @Field
    private List<String> bidangPerusahaan;

    @Field
    private String nomorTelepon;

    @Field
    private String alamat;

    @Field
    private String profilSingkat;

    @Field
    private String website;

    @Field
    private String fotoProfil;
}

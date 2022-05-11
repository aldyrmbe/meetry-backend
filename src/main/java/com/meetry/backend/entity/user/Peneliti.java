package com.meetry.backend.entity.user;

import com.meetry.backend.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "peneliti")
public class Peneliti {
    @Id
    private String id;

    private Role role;

    private String namaLengkap;

    private String email;

    private String password;

    private String NIDN;

    private String perguruanTinggi;

    private String programStudi;

    private String jenisKelamin;

    private Long tanggalLahir;

    private String nomorKTP;

    private String nomorTelepon;

    private String alamatLengkap;

    private String acadstaffLink;

    private String fotoProfil;
}

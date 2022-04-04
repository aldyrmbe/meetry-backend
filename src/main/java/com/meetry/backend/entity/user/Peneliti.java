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

    @Field(value = "namaLengkap")
    private String namaLengkap;

    @Field(value = "email")
    private String email;

    @Field(value = "password")
    private String password;

    @Field(value = "role")
    private Role role;

    @Field(value = "NIDN")
    private String NIDN;

    @Field(value = "perguruanTinggi")
    private String perguruanTinggi;

    @Field(value = "programStudi")
    private String programStudi;

    @Field(value = "jenisKelamin")
    private String jenisKelamin;

    @Field(value = "nomorKTP")
    private String nomorKTP;

    @Field(value = "tanggalLahir")
    private Long tanggalLahir;

    @Field(value = "nomorTelepon")
    private String nomorTelepon;

    @Field(value = "alamatLengkap")
    private String alamatLengkap;

    @Field(value = "bioSingkat")
    private String bioSingkat;

    @Field(value = "website")
    private String website;

    @Field(value = "fotoProfil")
    private String fotoProfil;
}

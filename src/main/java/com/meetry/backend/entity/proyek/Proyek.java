package com.meetry.backend.entity.proyek;

import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
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
@Document(collection = "proyek")
public class Proyek {

    @Id
    private String id;

    @Field
    private Long createdAt;

    @Field
    private StatusProyek status;

    @Field
    private Role pemohon;

    @Field
    private Partisipan partisipan;

    @Field
    private String judulProyek;

    @Field
    private Periode periode;

    @Field
    private String bidang;

    @Field
    private String latarBelakang;

    @Field
    private String tujuan;

    @Field
    private String sasaran;

    @Field
    private String output;

    @Field
    private String kebermanfaatanProduk;

    @Field
    private String indikatorKesuksesan;

    @Field
    private String tingkatKesiapan;

    @Field
    private List<KebutuhanProyek> listKebutuhanProyek;

    @Field
    private List<String> linkPendukung;

    @Field
    private List<DokumenPendukung> dokumenPendukung;

    @Field
    private String whatsappGroupLink;

    @Field
    private List<DokumenPendukung> dokumenKontrak;

}

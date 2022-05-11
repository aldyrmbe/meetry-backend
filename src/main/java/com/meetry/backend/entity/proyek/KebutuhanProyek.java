package com.meetry.backend.entity.proyek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KebutuhanProyek {
    private String kebutuhanProyek;
    private String bentukKolaborasi;
    private String penjelasanTambahan;
    private String partisipan;
}

package com.meetry.backend.entity.proyek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DokumenPendukung {
    private String nama;
    private String url;
}

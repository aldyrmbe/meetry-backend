package com.meetry.backend.entity.proyek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Periode {
    private Long mulai;
    private Long selesai;
}

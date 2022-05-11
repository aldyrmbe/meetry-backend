package com.meetry.backend.entity.proyek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partisipan {
    private List<String> peneliti;
    private List<String> mitra;
    private String accountOfficer;
}

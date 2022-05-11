package com.meetry.backend.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPenelitiProfileWebResponse {
    private String nama;
    private String universitas;
    private String programStudi;
    private String email;
    private String profileUrl;
}

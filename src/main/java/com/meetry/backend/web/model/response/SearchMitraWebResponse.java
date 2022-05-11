package com.meetry.backend.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMitraWebResponse {

  private PaginationData paginationData;

  private List<MitraDetail> mitraList;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MitraDetail {

    private String id;

    private String fotoProfil;

    private String nama;

    private String bidang;

    private String alamat;

    private String profileUrl;
  }
}

package com.meetry.backend.helper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPerguruanTinggiDataResponse {

  private int success;

  private String message;

  private List<PerguruanTinggi> data;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PerguruanTinggi {

    private String kode;

    private String nama;

    private String kota;
  }
}

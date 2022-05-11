package com.meetry.backend.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchAccountOfficerWebResponse {

  private PaginationData paginationData;

  private List<AccountOfficerDetail> accountOfficerList;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AccountOfficerDetail {

    private String id;

    private String fotoProfil;

    private String nama;

    private String email;
  }
}

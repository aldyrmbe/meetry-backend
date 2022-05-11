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
public class GetAccountOfficerListWebResponse {
    private PaginationData paginationData;
    private List<AccountOfficerDetailsWebResponse> accountOfficerList;
}

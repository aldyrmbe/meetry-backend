package com.meetry.backend.command.impl;

import com.meetry.backend.command.SearchMitraCommand;
import com.meetry.backend.command.model.SearchMitraCommandRequest;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.PaginationData;
import com.meetry.backend.web.model.response.SearchMitraWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SearchMitraCommandImpl implements SearchMitraCommand {

  private final MitraRepository mitraRepository;
  private final ProyekHelper proyekHelper;

  @Override
  public DefaultResponse<SearchMitraWebResponse> execute(SearchMitraCommandRequest commandRequest) {

    Page<Mitra> mitraPage = getMitraPage(commandRequest);

    return DefaultResponse.<SearchMitraWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toSearchMitraWebResponse(mitraPage))
        .build();
  }

  private Page<Mitra> getMitraPage(SearchMitraCommandRequest commandRequest) {

    return mitraRepository.searchMitraByName(commandRequest.getSearchQuery(), commandRequest.getPage());
  }

  private SearchMitraWebResponse toSearchMitraWebResponse(Page<Mitra> mitraPage) {

    PaginationData paginationData = PaginationData.builder()
        .currentPage(mitraPage.getPageable()
            .getPageNumber() + 1)
        .totalPage(mitraPage.getTotalPages())
        .build();

    List<SearchMitraWebResponse.MitraDetail> mitraList = mitraPage.getContent()
        .stream()
        .map(mitra -> SearchMitraWebResponse.MitraDetail.builder()
            .id(mitra.getId())
            .fotoProfil(mitra.getFotoProfil())
            .nama(mitra.getNamaPerusahaan())
            .alamat(mitra.getAlamat())
            .bidang(proyekHelper.getFormattedNames(mitra.getBidangPerusahaan()))
            .profileUrl(String.format("/%s", mitra.getId()))
            .build())
        .collect(Collectors.toList());

    return SearchMitraWebResponse.builder()
        .paginationData(paginationData)
        .mitraList(mitraList)
        .build();
  }
}

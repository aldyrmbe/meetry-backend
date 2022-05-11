package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetListProyekCommand;
import com.meetry.backend.command.model.GetListProyekCommandRequest;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.Partisipan;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.ListProyekWebResponse;
import com.meetry.backend.web.model.response.PaginationData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetListProyekCommandImpl implements GetListProyekCommand {

  private final ProyekRepository proyekRepository;

  private final PenelitiRepository penelitiRepository;

  private final MitraRepository mitraRepository;

  private final ProyekHelper proyekHelper;

  @Override
  public DefaultResponse<ListProyekWebResponse> execute(GetListProyekCommandRequest commandRequest) {

    Page<Proyek> proyekList = getProyekPage(commandRequest);

    return DefaultResponse.<ListProyekWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toListProyekWebResponse(proyekList, commandRequest))
        .build();
  }

  private Page<Proyek> getProyekPage(GetListProyekCommandRequest commandRequest) {

    Session session = commandRequest.getSession();
    StatusProyek status = commandRequest.getStatus();
    String searchQuery = commandRequest.getSearchQuery();
    int page = commandRequest.getPage();
    return proyekRepository.getProyekList(session, status, searchQuery, page);
  }

  private List<ListProyekWebResponse.ProyekData> getListProyekData(List<Proyek> proyekList, Role role) {

    return proyekList.stream()
        .map(proyek -> {
          List<String> partisipan = getPartisipan(proyek.getPartisipan(), role);
          return ListProyekWebResponse.ProyekData.builder()
              .id(proyek.getId())
              .pemohon(proyek.getPemohon())
              .judul(proyek.getJudulProyek())
              .partisipan(proyekHelper.getFormattedNames(partisipan))
              .status(proyek.getStatus())
              .build();
        })
        .collect(Collectors.toList());
  }

  private List<String> getPartisipan(Partisipan partisipan, Role role) {

    List<String> penelitiIdList = partisipan.getPeneliti();
    List<String> mitraIdList = partisipan.getMitra();

    if (Role.PENELITI.equals(role)) {
      return mitraIdList.stream()
          .map(mitraId -> {
            Mitra mitra = mitraRepository.findById(mitraId)
                .orElseThrow(() -> new BaseException("Mitra tidak ditemukan"));
            return mitra.getNamaPerusahaan();
          })
          .collect(Collectors.toList());
    }

    if (Role.MITRA.equals(role)) {
      return penelitiIdList.stream()
          .map(penelitiId -> {
            Peneliti peneliti = penelitiRepository.findById(penelitiId)
                .orElseThrow(() -> new BaseException("Peneliti tidak ditemukan"));
            return peneliti.getNamaLengkap();
          })
          .collect(Collectors.toList());
    }

    List<String> namaPenelitiList = penelitiIdList.stream()
        .map(penelitiId -> {
          Peneliti peneliti = penelitiRepository.findById(penelitiId)
              .orElseThrow(() -> new BaseException("Peneliti tidak ditemukan"));
          return peneliti.getNamaLengkap();
        })
        .collect(Collectors.toList());

    List<String> namaMitraList = mitraIdList.stream()
        .map(mitraId -> {
          Mitra mitra = mitraRepository.findById(mitraId)
              .orElseThrow(() -> new BaseException("Mitra tidak ditemukan"));
          return mitra.getNamaPerusahaan();
        })
        .collect(Collectors.toList());

    List<String> namaPartisipanList = new ArrayList<>();
    namaPartisipanList.addAll(namaPenelitiList);
    namaPartisipanList.addAll(namaMitraList);

    return namaPartisipanList;
  }

  private PaginationData getPaginationData(Page<Proyek> proyekPage) {

    return PaginationData.builder()
        .currentPage(proyekPage.getPageable()
            .getPageNumber() + 1)
        .totalPage(proyekPage.getTotalPages())
        .build();
  }

  private ListProyekWebResponse toListProyekWebResponse(Page<Proyek> proyekPage,
      GetListProyekCommandRequest commandRequest) {

    PaginationData paginationData = getPaginationData(proyekPage);
    List<ListProyekWebResponse.ProyekData> proyekData =
        getListProyekData(proyekPage.getContent(), commandRequest.getSession()
            .getRole());

    return ListProyekWebResponse.builder()
        .paginationData(paginationData)
        .proyekData(proyekData)
        .build();
  }

}

package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetListProyekOnRequestCommand;
import com.meetry.backend.command.model.GetListProyekOnRequestCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetListProyekOnRequestWebRequest;
import com.meetry.backend.web.model.response.PaginationData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetListProyekOnRequestCommandImpl implements GetListProyekOnRequestCommand {

  private final ProyekRepository proyekRepository;

  private final PenelitiRepository penelitiRepository;

  private final MitraRepository mitraRepository;

  @Override
  public DefaultResponse<GetListProyekOnRequestWebRequest> execute(
      GetListProyekOnRequestCommandRequest commandRequest) {

    Page<Proyek> pageProyek = getProyekPage(commandRequest);

    return DefaultResponse.<GetListProyekOnRequestWebRequest>builder()
        .code(200)
        .status("OK")
        .data(toGetListProyekOnRequest(pageProyek))
        .build();
  }

  private Page<Proyek> getProyekPage(GetListProyekOnRequestCommandRequest commandRequest) {

    List<Role> validPemohon = Arrays.asList(Role.PENELITI, Role.MITRA);
    if (!validPemohon.contains(commandRequest.getPemohon())) {
      throw new BaseException("Parameter pemohon tidak valid");
    }

    return proyekRepository.getProyekOnRequest(commandRequest.getPemohon(), commandRequest.getSearchQuery(),
        commandRequest.getPage());
  }

  private GetListProyekOnRequestWebRequest toGetListProyekOnRequest(Page<Proyek> proyekPage) {

    PaginationData paginationData =
        PaginationData.builder()
            .currentPage(proyekPage.getPageable()
                .getPageNumber() + 1)
            .totalPage(proyekPage.getTotalPages())
            .build();

    List<GetListProyekOnRequestWebRequest.ProyekDetail> proyekList = proyekPage.getContent()
        .stream()
        .map(proyek -> {
          Pair<String, String> dataPemohon = getPemohon(proyek);
          return GetListProyekOnRequestWebRequest.ProyekDetail.builder()
              .id(proyek.getId())
              .fotoProfil(dataPemohon.getSecond())
              .judul(proyek.getJudulProyek())
              .bidang(proyek.getBidang())
              .pemohon(dataPemohon.getFirst())
              .build();
        })
        .collect(Collectors.toList());

    return GetListProyekOnRequestWebRequest.builder()
        .paginationData(paginationData)
        .proyekList(proyekList)
        .build();
  }

  private Pair<String, String> getPemohon(Proyek proyek) {

    if (Role.PENELITI.equals(proyek.getPemohon())) {
      String id = proyek.getPartisipan()
          .getPeneliti()
          .get(0);
      Peneliti peneliti = penelitiRepository.findById(id)
          .get();
      return Pair.of(peneliti.getNamaLengkap(), peneliti.getFotoProfil());
    }

    String id = proyek.getPartisipan()
        .getMitra()
        .get(0);
    Mitra mitra = mitraRepository.findById(id)
        .get();
    return Pair.of(mitra.getNamaPerusahaan(), mitra.getFotoProfil());
  }

}

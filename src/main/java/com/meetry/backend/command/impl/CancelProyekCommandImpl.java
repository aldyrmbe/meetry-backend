package com.meetry.backend.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetry.backend.command.CancelProyekCommand;
import com.meetry.backend.command.model.CancelProyekCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.KebutuhanProyek;
import com.meetry.backend.entity.proyek.Partisipan;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.NotificationHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CancelProyekCommandImpl implements CancelProyekCommand {

  private final ProyekHelper proyekHelper;

  private final ProyekRepository proyekRepository;

  private final NotificationHelper notificationHelper;

  private final AuthHelper authHelper;

  @Override
  public BaseResponse execute(CancelProyekCommandRequest commandRequest) {

    Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
    authHelper.checkAccountOfficerAuthorization(commandRequest.getSession(), proyek);
    proyekHelper.verifyProyekStatusToCancelProyek(proyek);
    cancelProyek(proyek);

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Proyek berhasil dibatalkan.")
        .build();
  }

  @SneakyThrows
    private void cancelProyek(Proyek proyek){

        proyek.setStatus(StatusProyek.DIBATALKAN);
        proyekRepository.save(proyek);
        notificationHelper.sendNotificationForClosingProyek(proyek);

        initializeNewProyek(proyek);
    }

  private void initializeNewProyek(Proyek proyek) {

    Proyek newProyek = Proyek.builder()
        .createdAt(Instant.now()
            .toEpochMilli())
        .status(StatusProyek.DALAM_PENGAJUAN)
        .pemohon(proyek.getPemohon())
        .partisipan(getPartisipan(proyek))
        .judulProyek(proyek.getJudulProyek())
        .periode(proyek.getPeriode())
        .bidang(proyek.getBidang())
        .latarBelakang(proyek.getLatarBelakang())
        .tujuan(proyek.getTujuan())
        .sasaran(proyek.getSasaran())
        .output(proyek.getOutput())
        .kebermanfaatanProduk(proyek.getKebermanfaatanProduk())
        .indikatorKesuksesan(proyek.getIndikatorKesuksesan())
        .tingkatKesiapan(proyek.getTingkatKesiapan())
        .listKebutuhanProyek(getListKebutuhanProyek(proyek.getListKebutuhanProyek()))
        .linkPendukung(proyek.getLinkPendukung())
        .dokumenPendukung(proyek.getDokumenPendukung())
        .build();

    proyekRepository.save(newProyek);

  }

  private Partisipan getPartisipan(Proyek proyek) {

    if (Role.PENELITI.equals(proyek.getPemohon())) {
      return Partisipan.builder()
          .peneliti(proyek.getPartisipan()
              .getPeneliti())
          .mitra(new ArrayList<>())
          .build();
    }

    return Partisipan.builder()
        .mitra(proyek.getPartisipan()
            .getMitra())
        .peneliti(new ArrayList<>())
        .build();
  }

  private List<KebutuhanProyek> getListKebutuhanProyek(List<KebutuhanProyek> kebutuhanProyekList) {

    return kebutuhanProyekList.stream()
        .map(kebutuhanProyek -> KebutuhanProyek.builder()
            .kebutuhanProyek(kebutuhanProyek.getKebutuhanProyek())
            .bentukKolaborasi(kebutuhanProyek.getBentukKolaborasi())
            .penjelasanTambahan(kebutuhanProyek.getPenjelasanTambahan())
            .build())
        .collect(Collectors.toList());
  }
}

package com.meetry.backend.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetry.backend.command.AjukanProyekCommand;
import com.meetry.backend.command.model.AjukanProyekCommandRequest;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.folder.Folder;
import com.meetry.backend.entity.proyek.*;
import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.model.GoFileUploadResponse;
import com.meetry.backend.repository.FolderRepository;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.web.model.request.AjukanProyekWebRequest;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AjukanProyekCommandImpl implements AjukanProyekCommand {

  private final ObjectMapper objectMapper;

  private final ClientHelper clientHelper;

  private final ProyekRepository proyekRepository;

  private final FolderRepository folderRepository;

  @Override
  @SneakyThrows
  public BaseResponse execute(AjukanProyekCommandRequest commandRequest) {

    AjukanProyekWebRequest webRequest = objectMapper.readValue(commandRequest.getData(), AjukanProyekWebRequest.class);
    List<DokumenPendukung> dokumenPendukung = getFileUrls(commandRequest.getFiles());
    Proyek proyek = proyekRepository.save(toProyek(webRequest, dokumenPendukung, commandRequest.getSession()));
    constructFolder(proyek.getId());

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Proyek berhasil diajukan.")
        .build();
  }

  private List<DokumenPendukung> getFileUrls(MultipartFile[] files) {

    if (Objects.isNull(files)) {
      return new ArrayList<>();
    }
    List<MultipartFile> multipartFiles = Arrays.asList(files);
    return multipartFiles.stream()
        .map(file -> {
          GoFileUploadResponse response = clientHelper.uploadFile(file);
          return DokumenPendukung.builder()
              .nama(response.getData()
                  .getFileName())
              .url(response.getData()
                  .getDownloadPage())
              .build();
        })
        .collect(Collectors.toList());
  }

  private Proyek toProyek(AjukanProyekWebRequest request, List<DokumenPendukung> dokumenPendukung, Session session) {

    List<String> peneliti = new ArrayList<>();
    List<String> mitra = new ArrayList<>();

    if (session.getRole()
        .equals(Role.PENELITI)) {
      peneliti.add(session.getId());
    }

    if (session.getRole()
        .equals(Role.MITRA)) {
      mitra.add(session.getId());
    }

    Partisipan partisipan = Partisipan.builder()
        .peneliti(peneliti)
        .mitra(mitra)
        .build();

    Periode periode = Periode.builder()
        .mulai(request.getPeriodeMulai())
        .selesai(request.getPeriodeSelesai())
        .build();

    List<KebutuhanProyek> listKebutuhanProyek = request.getKebutuhanProyek()
        .stream()
        .map(kebutuhanProyek -> KebutuhanProyek.builder()
            .kebutuhanProyek(kebutuhanProyek.getKebutuhanProyek())
            .bentukKolaborasi(kebutuhanProyek.getBentukKolaborasi())
            .penjelasanTambahan(kebutuhanProyek.getPenjelasanTambahan())
            .build())
        .collect(Collectors.toList());

    return Proyek.builder()
        .createdAt(Instant.now()
            .toEpochMilli())
        .status(StatusProyek.DALAM_PENGAJUAN)
        .pemohon(session.getRole())
        .partisipan(partisipan)
        .judulProyek(request.getJudul())
        .periode(periode)
        .bidang(request.getBidang())
        .latarBelakang(request.getLatarBelakang())
        .tujuan(request.getTujuan())
        .sasaran(request.getSasaran())
        .output(request.getOutput())
        .kebermanfaatanProduk(request.getKebermanfaatanProduk())
        .indikatorKesuksesan(request.getIndikatorKesuksesan())
        .tingkatKesiapan(request.getTingkatKesiapan())
        .listKebutuhanProyek(listKebutuhanProyek)
        .linkPendukung(Optional.ofNullable(request.getLinkPendukung()).orElse(new ArrayList<>()))
        .dokumenPendukung(dokumenPendukung)
        .build();
  }

  private void constructFolder(String proyekId) {

    List<String> defaultFolderNames = Arrays.asList("Persiapan", "Kegiatan", "Evaluasi", "Lainnya");
    List<Folder> folders = defaultFolderNames.stream()
        .map(folderName -> Folder.builder()
            .proyekId(proyekId)
            .folderName(folderName)
            .build())
        .collect(Collectors.toList());

    folderRepository.saveAll(folders);
  }
}

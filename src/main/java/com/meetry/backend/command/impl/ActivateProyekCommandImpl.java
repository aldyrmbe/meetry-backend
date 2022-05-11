package com.meetry.backend.command.impl;

import com.meetry.backend.command.ActivateProyekCommand;
import com.meetry.backend.command.model.ActivateProyekCommandRequest;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.DokumenPendukung;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.NotificationHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.helper.model.GoFileUploadResponse;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.exception.UnauthorizedException;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ActivateProyekCommandImpl implements ActivateProyekCommand {

  private final ClientHelper clientHelper;

  private final ProyekHelper proyekHelper;

  private final AuthHelper authHelper;

  private final ProyekRepository proyekRepository;

  private final NotificationHelper notificationHelper;

  public static final String PDF_FILE_EXTENSION = "application/pdf";

  @Override
  public BaseResponse execute(ActivateProyekCommandRequest commandRequest) {

    Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
    authHelper.checkAccountOfficerAuthorization(commandRequest.getSession(), proyek);
    List<DokumenPendukung> dokumenKontrak = getFileUrls(commandRequest.getFiles());
    activateProyek(proyek, dokumenKontrak);

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Aktivasi proyek berhasil.")
        .build();
  }

  private void verifyProyekStatus(StatusProyek status) {

    List<StatusProyek> invalidStatusToUpdate =
        Arrays.asList(StatusProyek.DALAM_PENGAJUAN, StatusProyek.AKTIF, StatusProyek.DIBATALKAN, StatusProyek.SELESAI);
    if (invalidStatusToUpdate.contains(status)) {
      throw new BaseException("Gagal mengupdate status proyek.");
    }
  }

  private void activateProyek(Proyek proyek, List<DokumenPendukung> dokumenKontrak) {

    verifyProyekStatus(proyek.getStatus());
    proyek.setStatus(StatusProyek.AKTIF);
    proyek.setDokumenKontrak(dokumenKontrak);

    Proyek activatedProyek = proyekRepository.save(proyek);
    notificationHelper.sendNotificationForProyekOnActive(activatedProyek);
  }

  private List<DokumenPendukung> getFileUrls(MultipartFile[] files) {

    verifyRequiredFiles(files);
    return Arrays.stream(files)
        .map(file -> {
          GoFileUploadResponse response = clientHelper.uploadFile(file);
          System.out.println(response);
          return DokumenPendukung.builder()
              .nama(response.getData()
                  .getFileName())
              .url(response.getData()
                  .getDownloadPage())
              .build();
        })
        .collect(Collectors.toList());
  }

  private void verifyRequiredFiles(MultipartFile[] files) {

    if (files.length == 0) {
      throw new BaseException("File kontrak perlu diupload!");
    }

    Arrays.stream(files)
        .forEach(file -> {
          if (!PDF_FILE_EXTENSION.equals(file.getContentType())) {
            throw new BaseException("Jenis file yang diupload harus pdf.");
          }
        });
  }
}

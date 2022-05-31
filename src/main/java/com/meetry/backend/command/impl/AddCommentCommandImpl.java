package com.meetry.backend.command.impl;

import com.meetry.backend.command.AddCommentCommand;
import com.meetry.backend.command.model.AddCommentCommandRequest;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.komentar.File;
import com.meetry.backend.entity.komentar.Komentar;
import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.entity.user.ERIC;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.model.GoFileUploadResponse;
import com.meetry.backend.repository.KomentarRepository;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.repository.user.ERICRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AddCommentCommandImpl implements AddCommentCommand {

  private final KomentarRepository komentarRepository;

  private final PenelitiRepository penelitiRepository;

  private final MitraRepository mitraRepository;

  private final ERICRepository ericRepository;

  private final AccountOfficerRepository accountOfficerRepository;

  private final AuthHelper authHelper;

  private final ClientHelper clientHelper;

  private final ProyekRepository proyekRepository;

  @Override
  public BaseResponse execute(AddCommentCommandRequest commandRequest) {

    authHelper.checkUserAuthorizationForLogbooks(commandRequest.getSession(), commandRequest.getProyekId(),
        commandRequest.getSubFolderId());
    saveComment(commandRequest);

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Komentar berhasil ditambahkan.")
        .build();
  }

  private void saveComment(AddCommentCommandRequest commandRequest) {

    List<File> files = getFiles(commandRequest);
    if (Objects.nonNull(files)) {
      List<com.meetry.backend.entity.proyek.File> filesToSave = files.stream()
              .map(file -> com.meetry.backend.entity.proyek.File.builder()
                  .url(file.getUrl())
                  .name(file.getName())
                  .build())
                  .collect(Collectors.toList());
      proyekRepository.saveFiles(commandRequest.getProyekId(), filesToSave);
    }

    Komentar comment = Komentar.builder()
        .logbookId(commandRequest.getLogbookId())
        .createdAt(Instant.now()
            .toEpochMilli())
        .senderName(getSender(commandRequest.getSession()).getFirst())
        .profilePhoto(getSender(commandRequest.getSession()).getSecond())
        .content(commandRequest.getContent())
        .files(files)
        .build();

    komentarRepository.save(comment);
  }

  private Pair<String, String> getSender(Session session) {

    if (Role.PENELITI.equals(session.getRole())) {
      Peneliti peneliti = penelitiRepository.findById(session.getId())
          .get();
      return Pair.of(peneliti.getNamaLengkap(), peneliti.getFotoProfil());
    }

    if (Role.MITRA.equals(session.getRole())) {
      Mitra mitra = mitraRepository.findById(session.getId())
          .get();
      return Pair.of(mitra.getNamaPerusahaan(), mitra.getFotoProfil());
    }

    if (Role.ACCOUNT_OFFICER.equals(session.getRole())) {
      AccountOfficer accountOfficer = accountOfficerRepository.findById(session.getId())
          .get();
      return Pair.of(accountOfficer.getName(), accountOfficer.getProfilePhoto());
    }

    ERIC eric = ericRepository.findById(session.getId())
        .get();
    return Pair.of(eric.getName(), eric.getProfilePhoto());
  }

  private List<File> getFiles(AddCommentCommandRequest commandRequest) {

    if (Objects.isNull(commandRequest.getFiles())) {
      return null;
    }

    return Arrays.stream(commandRequest.getFiles())
        .map(file -> {
          GoFileUploadResponse response = clientHelper.uploadFile(file);
          return File.builder()
              .name(response.getData()
                  .getFileName())
              .url(response.getData()
                  .getDownloadPage())
              .build();
        })
        .collect(Collectors.toList());
  }
}

package com.meetry.backend.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetry.backend.command.RegisterPenelitiCommand;
import com.meetry.backend.command.model.RegisterPenelitiCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.notifikasi.Notifikasi;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.model.ImgBBUploadResponse;
import com.meetry.backend.repository.NotificationRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.request.RegisterPenelitiWebRequest;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Objects;

@Component
@AllArgsConstructor
public class RegisterPenelitiCommandImpl implements RegisterPenelitiCommand {

  private final PenelitiRepository penelitiRepository;

  private final ObjectMapper objectMapper;

  private static final String GET_UI_AVATARS = "https://ui-avatars.com/api/?name=";

  private final ClientHelper clientHelper;

  private final NotificationRepository notificationRepository;

  @Override
  @SneakyThrows
  public BaseResponse execute(RegisterPenelitiCommandRequest commandRequest) {

    RegisterPenelitiWebRequest webRequest =
        objectMapper.readValue(commandRequest.getData(), RegisterPenelitiWebRequest.class);
    checkEmailAvailability(webRequest.getEmail());
    Peneliti peneliti = toPeneliti(webRequest);
    if (Objects.isNull(commandRequest.getFotoProfil())) {
      peneliti.setFotoProfil(generateDefaultProfilePicture(webRequest.getNamaLengkap()));
      Peneliti savedPeneliti = penelitiRepository.save(peneliti);
      initNotification(savedPeneliti.getId());
    } else {
      uploadProfilePicture(peneliti, commandRequest.getFotoProfil());
    }

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Registrasi peneliti berhasil.")
        .build();
  }

  private void checkEmailAvailability(String email) {

    if (penelitiRepository.findByEmail(email)
        .isPresent()) {
      throw new BaseException("Email sudah terdaftar.");
    }
  }

  private Peneliti toPeneliti(RegisterPenelitiWebRequest request) {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(request.getPassword());
    return Peneliti.builder()
        .role(Role.PENELITI)
        .namaLengkap(request.getNamaLengkap())
        .email(request.getEmail())
        .password(hashedPassword)
        .NIDN(request.getNIDN())
        .perguruanTinggi(request.getPerguruanTinggi())
        .programStudi(request.getProgramStudi())
        .jenisKelamin(request.getJenisKelamin())
        .nomorKTP(request.getNomorKTP())
        .tanggalLahir(request.getTanggalLahir())
        .nomorTelepon(request.getNomorTelepon())
        .alamatLengkap(request.getAlamatLengkap())
        .acadstaffLink(request.getAcadstaffLink())
        .build();
  }

  private String generateDefaultProfilePicture(String name) {

    String validName = name.replace(" ", "+");
    return GET_UI_AVATARS + validName;
  }

  private void initNotification(String penelitiId) {

    Notifikasi notifikasi = Notifikasi.builder()
        .userId(penelitiId)
        .items(new ArrayList<>())
        .hasNewNotification(false)
        .build();
    notificationRepository.save(notifikasi);
  }

  @Async
  public void uploadProfilePicture(Peneliti peneliti, MultipartFile image) {

    ImgBBUploadResponse uploadResponse = clientHelper.uploadImage(image);
    String imageUrl = uploadResponse.getData()
        .getImage()
        .getUrl();
    peneliti.setFotoProfil(imageUrl);
    Peneliti savedPeneliti = penelitiRepository.save(peneliti);
    initNotification(savedPeneliti.getId());
  }
}

package com.meetry.backend.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetry.backend.command.RegisterMitraCommand;
import com.meetry.backend.command.model.RegisterMitraCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.model.ImgBBUploadResponse;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.request.RegisterMitraWebRequest;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RegisterMitraCommandImpl implements RegisterMitraCommand {

  private final MitraRepository mitraRepository;

  private final ObjectMapper objectMapper;

  private final ClientHelper clientHelper;

  private static final String GET_UI_AVATARS = "https://ui-avatars.com/api/?name=";

  @Override
  @SneakyThrows
  public BaseResponse execute(RegisterMitraCommandRequest commandRequest) {

    RegisterMitraWebRequest webRequest =
        objectMapper.readValue(commandRequest.getData(), RegisterMitraWebRequest.class);
    Mitra mitra = toMitra(webRequest);
    checkEmailAvailability(webRequest.getEmail());
    if (Objects.isNull(commandRequest.getFotoProfil())) {
      mitra.setFotoProfil(generateDefaultProfilePicture(webRequest.getNamaPerusahaan()));
      mitraRepository.save(mitra);
    } else {
      uploadProfilePicture(mitra, commandRequest.getFotoProfil());
    }

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Registrasi mitra berhasil.")
        .build();
  }

  private void checkEmailAvailability(String email) {

    if (mitraRepository.findByEmail(email)
        .isPresent()) {
      throw new BaseException("Email sudah terdaftar.");
    }
  }

  private Mitra toMitra(RegisterMitraWebRequest request) {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(request.getPassword());
    return Mitra.builder()
        .email(request.getEmail())
        .password(hashedPassword)
        .role(Role.MITRA)
        .namaPerusahaan(request.getNamaPerusahaan())
        .alamat(request.getAlamat())
        .bidangPerusahaan(request.getBidangPerusahaan())
        .jenisPerusahaan(request.getJenisPerusahaan())
        .nomorTelepon(request.getNomorTelepon())
        .profilSingkat(request.getProfilSingkat())
        .website(Optional.ofNullable(request.getWebsite()).orElse(null))
        .build();
  }

  private String generateDefaultProfilePicture(String name) {

    String validName = name.replace(" ", "+");
    return GET_UI_AVATARS + validName;
  }

  @Async
  public void uploadProfilePicture(Mitra mitra, MultipartFile image) {

    ImgBBUploadResponse uploadResponse = clientHelper.uploadImage(image);
    String imageUrl = uploadResponse.getData()
        .getImage()
        .getUrl();
    mitra.setFotoProfil(imageUrl);
    mitraRepository.save(mitra);
  }
}

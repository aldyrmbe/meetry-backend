package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetMitraProfileCommand;
import com.meetry.backend.command.model.GetMitraProfileCommandRequest;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetMitraProfileWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetMitraProfileCommandImpl implements GetMitraProfileCommand {

  private final MitraRepository mitraRepository;

  @Override
  public DefaultResponse<GetMitraProfileWebResponse> execute(GetMitraProfileCommandRequest commandRequest) {

    Mitra mitra = getMitra(commandRequest.getMitraId());

    return DefaultResponse.<GetMitraProfileWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toGetMitraProfileWebResponse(mitra))
        .build();
  }

  private Mitra getMitra(String mitraId) {

    return mitraRepository.findById(mitraId)
        .orElseThrow(() -> new BaseException("Mitra tidak ditemukan."));
  }

  private GetMitraProfileWebResponse toGetMitraProfileWebResponse(Mitra mitra) {

    return GetMitraProfileWebResponse.builder()
        .namaPerusahaan(mitra.getNamaPerusahaan())
        .fotoProfil(mitra.getFotoProfil())
        .bidangPerusahaan(mitra.getBidangPerusahaan())
        .nomorTelepon(mitra.getNomorTelepon())
        .email(mitra.getEmail())
        .website(mitra.getWebsite())
        .profilSingkat(mitra.getProfilSingkat())
        .jenisPerusahaan(mitra.getJenisPerusahaan())
        .alamat(mitra.getAlamat())
        .build();
  }
}

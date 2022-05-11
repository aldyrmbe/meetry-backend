package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetPenelitiProfileCommand;
import com.meetry.backend.command.model.GetPenelitiProfileCommandRequest;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetPenelitiProfileWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetPenelitiProfileCommandImpl implements GetPenelitiProfileCommand {

  private final PenelitiRepository penelitiRepository;

  @Override
  public DefaultResponse<GetPenelitiProfileWebResponse> execute(GetPenelitiProfileCommandRequest commandRequest) {

    Peneliti peneliti = getPenelitiById(commandRequest.getPenelitiId());

    return DefaultResponse.<GetPenelitiProfileWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toGetPenelitiProfileWebResponse(peneliti))
        .build();
  }

  private Peneliti getPenelitiById(String penelitiId) {

    return penelitiRepository.findById(penelitiId)
        .orElseThrow(() -> new BaseException("Peneliti tidak ditemukan"));
  }

  private GetPenelitiProfileWebResponse toGetPenelitiProfileWebResponse(Peneliti peneliti) {

    return GetPenelitiProfileWebResponse.builder()
        .nama(peneliti.getNamaLengkap())
        .universitas(peneliti.getPerguruanTinggi())
        .programStudi(peneliti.getProgramStudi())
        .email(peneliti.getEmail())
        .profileUrl(peneliti.getAcadstaffLink())
        .build();

  }
}

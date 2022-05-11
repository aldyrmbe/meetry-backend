package com.meetry.backend.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetry.backend.command.GetIndonesianUniversitiesCommand;
import com.meetry.backend.command.model.GetIndonesianUniversitiesCommandRequest;
import com.meetry.backend.entity.Universitas;
import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.model.GetPerguruanTinggiDataResponse;
import com.meetry.backend.repository.UniversitasRepository;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetIndonesianUniversitiesCommandImpl implements GetIndonesianUniversitiesCommand {

  private final ClientHelper clientHelper;

  private final UniversitasRepository universitasRepository;

  private final ObjectMapper objectMapper;

  @Override
  public BaseResponse execute(GetIndonesianUniversitiesCommandRequest getIndonesianUniversitiesCommandRequest) {

    String perguruanTinggiListRawData = getPerguruanTinggi();
    List<GetPerguruanTinggiDataResponse.PerguruanTinggi> perguruanTinggiList = getListFromRawData(perguruanTinggiListRawData);
    List<Universitas> universitasList = toListUniversitas(perguruanTinggiList);
    universitasRepository.saveAll(universitasList);

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Berhasil!")
        .build();
  }

  private String getPerguruanTinggi() {

    return clientHelper.getPerguruanTingi();
  }

  @SneakyThrows
  private List<GetPerguruanTinggiDataResponse.PerguruanTinggi> getListFromRawData(String rawData){
    return objectMapper.readValue(rawData, GetPerguruanTinggiDataResponse.class)
        .getData();
  }

  private List<Universitas> toListUniversitas(List<GetPerguruanTinggiDataResponse.PerguruanTinggi> perguruanTinggiList){
    return perguruanTinggiList.stream()
        .map(perguruanTinggi -> Universitas.builder().name(perguruanTinggi.getNama()).build())
        .collect(Collectors.toList());
  }
}

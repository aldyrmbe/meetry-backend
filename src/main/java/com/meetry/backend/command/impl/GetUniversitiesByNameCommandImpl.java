package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetUniversitiesByNameCommand;
import com.meetry.backend.command.model.GetUniversitiesByNameCommandRequest;
import com.meetry.backend.entity.Universitas;
import com.meetry.backend.repository.UniversitasRepository;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetUniversitiesByNameWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetUniversitiesByNameCommandImpl implements GetUniversitiesByNameCommand {

  private final UniversitasRepository universitasRepository;

  @Override
  public DefaultResponse<List<GetUniversitiesByNameWebResponse>> execute(
      GetUniversitiesByNameCommandRequest commandRequest) {

    List<Universitas> universitasList = getUniversitasByName(commandRequest.getQuery());
    List<String> universityNames = toListString(universitasList);

    return DefaultResponse.<List<GetUniversitiesByNameWebResponse>>builder()
        .code(200)
        .status("OK")
        .data(toListGetUniversitiesByNameWebResponse(universityNames))
        .build();
  }

  private List<Universitas> getUniversitasByName(String name) {

    return universitasRepository.getUniversitasByName(name);
  }

  private List<String> toListString(List<Universitas> universitasList) {

    return universitasList.stream()
        .map(Universitas::getName)
        .collect(Collectors.toList());
  }

  private List<GetUniversitiesByNameWebResponse> toListGetUniversitiesByNameWebResponse(List<String> universityNames) {

    return universityNames.stream()
        .map(university -> GetUniversitiesByNameWebResponse.builder()
            .label(university)
            .value(university)
            .build())
        .collect(Collectors.toList());
  }
}

package com.meetry.backend.helper.impl;

import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.web.exception.BaseException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProyekHelperImpl implements ProyekHelper {

  private final ProyekRepository proyekRepository;

  @Override
  public Proyek findProyekById(String proyekId) {

    return proyekRepository.findById(proyekId)
        .orElseThrow(() -> new BaseException("Proyek tidak ditemukan"));
  }

  @Override
  public void verifyProyekStatusForLogbookOperations(Proyek proyek) {

    List<StatusProyek> invalidStatus =
        Arrays.asList(StatusProyek.DALAM_PENGAJUAN, StatusProyek.DIBATALKAN, StatusProyek.SELESAI);

    if(invalidStatus.contains(proyek.getStatus()))
      throw new BaseException("Tidak dapat melakukan operasi apapun pada logbook karena status proyek invalid.");
  }

  @Override
  public void verifyProyekStatusToCloseProyek(Proyek proyek) {

     if(!StatusProyek.AKTIF.equals(proyek.getStatus()))
       throw new BaseException("Gagal menyelesaikan proyek");
  }

  @Override
  public void verifyProyekStatusToCancelProyek(Proyek proyek) {

    List<StatusProyek> invalidStatus = Arrays.asList(StatusProyek.SELESAI, StatusProyek.DIBATALKAN);
    if(invalidStatus.contains(proyek.getStatus())){
      throw new BaseException("Gagal membatalkan proyek");
    }
  }

  @Override
  public String getFormattedNames(List<String> names) {

    if(names.isEmpty()){
      return "";
    }

    if (names.size() == 1) {
      return names.get(0);
    }

    if (names.size() == 2) {
      return String.format("%s dan %s", names.get(0), names.get(1));
    }

    int lastIndex = names.size() - 1;
    String lastName = names.get(lastIndex);
    names.remove(lastName);

    return String.format("%s, dan %s", String.join(", ", names), lastName);
  }
}

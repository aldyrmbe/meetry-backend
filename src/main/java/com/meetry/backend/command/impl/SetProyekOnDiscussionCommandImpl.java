package com.meetry.backend.command.impl;

import com.meetry.backend.command.SetProyekOnDiscussionCommand;
import com.meetry.backend.command.model.SetProyekOnDiscussionCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.KebutuhanProyek;
import com.meetry.backend.entity.proyek.Partisipan;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.NotificationHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class SetProyekOnDiscussionCommandImpl implements SetProyekOnDiscussionCommand {

  private final ProyekRepository proyekRepository;

  private final ProyekHelper proyekHelper;

  private final NotificationHelper notificationHelper;

  @Override
  public BaseResponse execute(SetProyekOnDiscussionCommandRequest commandRequest) {

    Proyek proyek = setProyekOnDiscussion(commandRequest);
    notificationHelper.sendNotificationForProyekOnDiscussion(proyek);

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("Proyek berhasil diset ke tahapan DALAM_DISKUSI")
        .build();
  }

  private Proyek setProyekOnDiscussion(SetProyekOnDiscussionCommandRequest commandRequest) {

    Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
    List<KebutuhanProyek> kebutuhanProyekList = proyek.getListKebutuhanProyek();

    List<StatusProyek> invalidStatusToUpdate =
        Arrays.asList(StatusProyek.DALAM_DISKUSI, StatusProyek.AKTIF, StatusProyek.SELESAI, StatusProyek.DIBATALKAN);
    if (invalidStatusToUpdate.contains(proyek.getStatus()))
      throw new BaseException("Gagal mengupdate status proyek.");

    Partisipan partisipan = proyek.getPartisipan();

    if (Role.PENELITI.equals(proyek.getPemohon())) {
      List<String> mitra = partisipan.getMitra();
      mitra.addAll(commandRequest.getPartisipan());
      partisipan.setMitra(mitra);

      IntStream.range(0, kebutuhanProyekList.size())
          .forEach(index -> kebutuhanProyekList.get(index).setPartisipan(mitra.get(index)));
    }

    if (Role.MITRA.equals(proyek.getPemohon())) {
      List<String> peneliti = partisipan.getPeneliti();
      peneliti.addAll(commandRequest.getPartisipan());
      partisipan.setPeneliti(peneliti);

      IntStream.range(0, kebutuhanProyekList.size())
          .forEach(index -> kebutuhanProyekList.get(index).setPartisipan(peneliti.get(index)));
    }

    partisipan.setAccountOfficer(commandRequest.getAccountOfficer());
    proyek.setPartisipan(partisipan);
    proyek.setWhatsappGroupLink(commandRequest.getWhatsappGroupLink());
    proyek.setStatus(StatusProyek.DALAM_DISKUSI);
    proyek.setListKebutuhanProyek(kebutuhanProyekList);
    return proyekRepository.save(proyek);
  }
}

package com.meetry.backend.helper.impl;

import com.meetry.backend.entity.constant.NotificationConstant;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.notifikasi.Notifikasi;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.helper.NotificationHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.NotificationRepository;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationHelperImpl implements NotificationHelper {

  private final NotificationRepository notificationRepository;

  private final MitraRepository mitraRepository;

  private final AccountOfficerRepository accountOfficerRepository;

  private final PenelitiRepository penelitiRepository;

  private final ProyekHelper proyekHelper;

  @Override
  public void sendNotificationForProyekOnDiscussion(Proyek proyek) {

    String whatsappGroupLink = proyek.getWhatsappGroupLink();
    String accountOfficerName = getAccountOfficerName(proyek.getPartisipan()
        .getAccountOfficer());

    if (Role.PENELITI.equals(proyek.getPemohon())) {
      String mitraNames = getMitraNames(proyek.getPartisipan()
          .getMitra());

      Notifikasi notifikasi = Notifikasi.builder()
          .receiver(Collections.singletonList(proyek.getPartisipan()
              .getPeneliti()
              .get(0)))
          .createdAt(Instant.now()
              .toEpochMilli())
          .title(NotificationConstant.ON_DISCUSSION_TITLE_PEMOHON_PENELITI)
          .description(String.format(NotificationConstant.ON_DISCUSSION_DESC_PEMOHON_PENELITI, mitraNames,
              whatsappGroupLink, accountOfficerName))
          .build();

      notificationRepository.save(notifikasi);
    }

    if (Role.MITRA.equals(proyek.getPemohon())) {
      String penelitiNames = getPenelitiNames(proyek.getPartisipan()
          .getPeneliti());

      Notifikasi notifikasi = Notifikasi.builder()
          .receiver(Collections.singletonList(proyek.getPartisipan()
              .getMitra()
              .get(0)))
          .createdAt(Instant.now()
              .toEpochMilli())
          .title(NotificationConstant.ON_DISCUSSION_TITLE_PEMOHON_MITRA)
          .description(String.format(NotificationConstant.ON_DISCUSSION_DESC_PEMOHON_MITRA, penelitiNames,
              whatsappGroupLink, accountOfficerName))
          .build();

      notificationRepository.save(notifikasi);
    }
  }

  @Override
  public void sendNotificationForProyekOnActive(Proyek proyek) {

    List<String> penelitiIds = proyek.getPartisipan()
        .getPeneliti();
    List<String> mitraIds = proyek.getPartisipan()
        .getMitra();
    Long createdAt = Instant.now().toEpochMilli();
    String judulProyek = proyek.getJudulProyek();

    Notifikasi notifikasiForPeneliti = Notifikasi.builder()
        .receiver(penelitiIds)
        .createdAt(createdAt)
        .title(NotificationConstant.ON_ACTIVE_TITLE_RECEIVER_PENELITI)
        .description(String.format(NotificationConstant.ON_ACTIVE_DESC, judulProyek))
        .build();

    Notifikasi notifikasiForMitra = Notifikasi.builder()
        .receiver(mitraIds)
        .createdAt(createdAt)
        .title(NotificationConstant.ON_ACTIVE_TITLE_RECEIVER_MITRA)
        .description(String.format(NotificationConstant.ON_ACTIVE_DESC, judulProyek))
        .build();

    notificationRepository.saveAll(Arrays.asList(notifikasiForPeneliti, notifikasiForMitra));
  }

  private String getAccountOfficerName(String accountOfficerId) {

    return accountOfficerRepository.findById(accountOfficerId)
        .get()
        .getName();
  }

  private String getMitraNames(List<String> mitraIds) {

    List<String> mitraNames = mitraIds.stream()
        .map(mitraId -> {
          Mitra mitra = mitraRepository.findById(mitraId)
              .get();
          return mitra.getNamaPerusahaan();
        })
        .distinct()
        .collect(Collectors.toList());

    return proyekHelper.getFormattedNames(mitraNames);
  }

  private String getPenelitiNames(List<String> penelitiIds) {

    List<String> penelitiNames = penelitiIds.stream()
        .map(penelitiId -> {
          Peneliti peneliti = penelitiRepository.findById(penelitiId)
              .get();
          return peneliti.getNamaLengkap();
        })
        .distinct()
        .collect(Collectors.toList());

    return proyekHelper.getFormattedNames(penelitiNames);
  }

  @Override
  public void sendNotificationForClosingProyek(Proyek proyek) {
    List<String> receiverIds = new ArrayList<>();
    receiverIds.addAll(proyek.getPartisipan().getPeneliti());
    receiverIds.addAll(proyek.getPartisipan().getMitra());
    Long createdAt = Instant.now().toEpochMilli();
    String judulProyek = proyek.getJudulProyek();

    Notifikasi notifikasi = Notifikasi.builder()
        .receiver(receiverIds)
        .createdAt(createdAt)
        .title(NotificationConstant.ON_CLOSE_TITLE)
        .description(String.format(NotificationConstant.ON_CLOSE_DESC, judulProyek))
        .build();

    notificationRepository.save(notifikasi);
  }
}

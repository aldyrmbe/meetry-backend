package com.meetry.backend.helper.impl;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.NotificationConstant;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.notifikasi.NotificationItem;
import com.meetry.backend.entity.notifikasi.Notifikasi;
import com.meetry.backend.entity.proyek.Partisipan;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.helper.NotificationHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.HasNewNotificationRepository;
import com.meetry.backend.repository.NotificationRepository;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.model.request.RealtimeNotificationWebSocketPayload;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.util.Pair;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class NotificationHelperImpl implements NotificationHelper {

  private final NotificationRepository notificationRepository;

  private final MitraRepository mitraRepository;

  private final AccountOfficerRepository accountOfficerRepository;

  private final PenelitiRepository penelitiRepository;

  private final ProyekHelper proyekHelper;

  private final SimpMessagingTemplate simpMessagingTemplate;

  private final HasNewNotificationRepository hasNewNotificationRepository;

  @Override
  public void sendNotificationForProyekOnDiscussion(Proyek proyek) {

    String whatsappGroupLink = proyek.getWhatsappGroupLink();
    String accountOfficerName = getAccountOfficerName(proyek.getPartisipan()
        .getAccountOfficer());

    Pair<List<String>, List<String>> partisipan = getPartisipan(proyek.getPartisipan());

    if (Role.PENELITI.equals(proyek.getPemohon())) {
      String mitraNames = getMitraNames(partisipan.getSecond());
      NotificationItem notificationItem = construct(NotificationConstant.ON_DISCUSSION_TITLE_PEMOHON_PENELITI,
          String.format(NotificationConstant.ON_DISCUSSION_DESC_PEMOHON_PENELITI, mitraNames,
              whatsappGroupLink, accountOfficerName),
          String.format(NotificationConstant.GENERAL_REDIRECTION_URL, "peneliti",
              proyek.getJudulProyek(), proyek.getId()));

      partisipan.getFirst()
          .forEach(penelitiId -> {
            updateRealtimeNotification(penelitiId, notificationItem);
          });
    }

    if (Role.MITRA.equals(proyek.getPemohon())) {
      String penelitiNames = getPenelitiNames(partisipan.getFirst());
      NotificationItem notificationItem = construct(NotificationConstant.ON_DISCUSSION_TITLE_PEMOHON_MITRA,
          String.format(NotificationConstant.ON_DISCUSSION_DESC_PEMOHON_MITRA, penelitiNames,
              whatsappGroupLink, accountOfficerName),
          String.format(NotificationConstant.GENERAL_REDIRECTION_URL, "mitra",
              proyek.getJudulProyek(), proyek.getId()));

      partisipan.getSecond()
          .forEach(mitraId -> {
            updateRealtimeNotification(mitraId, notificationItem);
          });
    }
  }

  @Override
  public void sendNotificationForProyekOnActive(Proyek proyek) {

    Pair<List<String>, List<String>> partisipan = getPartisipan(proyek.getPartisipan());

    partisipan.getFirst()
        .forEach(penelitiId -> {
          NotificationItem notificationItem = construct(NotificationConstant.ON_ACTIVE_TITLE_RECEIVER_PENELITI,
              String.format(NotificationConstant.ON_ACTIVE_DESC, proyek.getJudulProyek()),
              String.format(NotificationConstant.GENERAL_REDIRECTION_URL, "peneliti",
                  proyek.getJudulProyek(), proyek.getId()));

          updateRealtimeNotification(penelitiId, notificationItem);
        });

    partisipan.getSecond()
        .forEach(mitraId -> {
          NotificationItem notificationItem = construct(NotificationConstant.ON_ACTIVE_TITLE_RECEIVER_MITRA,
              String.format(NotificationConstant.ON_ACTIVE_DESC, proyek.getJudulProyek()),
              String.format(NotificationConstant.GENERAL_REDIRECTION_URL, "mitra",
                  proyek.getJudulProyek(), proyek.getId()));

          updateRealtimeNotification(mitraId, notificationItem);
        });
  }

  @Override
  public void sendNotificationForClosingProyek(Proyek proyek) {

    Pair<List<String>, List<String>> partisipan = getPartisipan(proyek.getPartisipan());

    partisipan.getFirst()
        .forEach(penelitiId -> {
          NotificationItem notificationItem = construct(NotificationConstant.ON_CLOSE_TITLE,
              String.format(NotificationConstant.ON_CLOSE_DESC, proyek.getJudulProyek()),
              String.format(NotificationConstant.GENERAL_REDIRECTION_URL, "peneliti",
                  proyek.getJudulProyek(), proyek.getId()));
          updateRealtimeNotification(penelitiId, notificationItem);
        });

    partisipan.getSecond()
        .forEach(mitraId -> {
          NotificationItem notificationItem = construct(NotificationConstant.ON_CLOSE_TITLE,
              String.format(NotificationConstant.ON_CLOSE_DESC, proyek.getJudulProyek()),
              String.format(NotificationConstant.GENERAL_REDIRECTION_URL, "mitra",
                  proyek.getJudulProyek(), proyek.getId()));
          updateRealtimeNotification(mitraId, notificationItem);
        });
  }

  @Override
  public void sendNotificationOnNewComment(String proyekId, String commentatorId, String commentatorName,
      String folderId, String subFolderId, String subFolderName) {

    Proyek proyek = proyekHelper.findProyekById(proyekId);
    Pair<List<String>, List<String>> partisipan = getPartisipan(proyek.getPartisipan());

    partisipan.getFirst()
        .stream()
        .filter(penelitiId -> !penelitiId.equals(commentatorId))
        .forEach(penelitiId -> {
          NotificationItem notificationItem = construct(
              NotificationConstant.NEW_COMMENT_TITLE,
              String.format(NotificationConstant.NEW_COMMENT_DESC, commentatorName, proyek.getJudulProyek()),
              String.format(
                  NotificationConstant.NEW_COMMENT_NOTIFICATION_REDIRECTION_URL,
                  "peneliti",
                  proyek.getJudulProyek(),
                  proyek.getId(),
                  folderId,
                  subFolderId,
                  subFolderName)
          );

          updateRealtimeNotification(penelitiId, notificationItem);
        });

    partisipan.getSecond()
        .stream()
        .filter(mitraId -> !mitraId.equals(commentatorId))
        .forEach(mitraId -> {
          NotificationItem notificationItem = construct(
              NotificationConstant.NEW_COMMENT_TITLE,
              String.format(NotificationConstant.NEW_COMMENT_DESC, commentatorName, proyek.getJudulProyek()),
              String.format(
                  NotificationConstant.NEW_COMMENT_NOTIFICATION_REDIRECTION_URL,
                  "mitra",
                  proyek.getJudulProyek(),
                  proyek.getId(),
                  folderId,
                  subFolderId,
                  subFolderName)
          );

          updateRealtimeNotification(mitraId, notificationItem);
        });
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

  private NotificationItem construct(String title, String description, String redirectionUrl) {

    return NotificationItem.builder()
        .id(new ObjectId().toString())
        .title(title)
        .description(description)
        .createdAt(Instant.now()
            .toEpochMilli())
        .isOpened(false)
        .redirectionUrl(redirectionUrl)
        .build();
  }

  private Pair<List<String>, List<String>> getPartisipan(Partisipan partisipan) {

    List<String> penelitiIds = partisipan.getPeneliti();
    List<String> mitraIds = partisipan.getMitra();

    return Pair.of(penelitiIds, mitraIds);
  }

  private void updateRealtimeNotification(String userId, NotificationItem notificationItem) {
    notificationRepository.saveNotification(userId, notificationItem);

    RealtimeNotificationWebSocketPayload payload = new RealtimeNotificationWebSocketPayload(true);
    simpMessagingTemplate.convertAndSend("/notification/" + userId, payload);
    simpMessagingTemplate.convertAndSend("/getLatestNotification/" + userId, notificationItem);
  }
}

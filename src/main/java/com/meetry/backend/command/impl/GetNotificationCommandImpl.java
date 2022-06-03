package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetNotificationCommand;
import com.meetry.backend.command.model.GetNotificationCommandRequest;
import com.meetry.backend.entity.CustomPage;
import com.meetry.backend.entity.notifikasi.NotificationItem;
import com.meetry.backend.repository.NotificationRepository;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.NotificationDataWebResponse;
import com.meetry.backend.web.model.response.PaginationData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetNotificationCommandImpl implements GetNotificationCommand {

  private final NotificationRepository notificationRepository;

  @Override
  public DefaultResponse<NotificationDataWebResponse> execute(GetNotificationCommandRequest commandRequest) {

    return DefaultResponse.<NotificationDataWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toNotificationDataWebResponse(commandRequest))
        .build();
  }

  private CustomPage<NotificationItem> getNotificationByUserId(GetNotificationCommandRequest commandRequest) {

    return notificationRepository.getNotificationPage(commandRequest.getSession()
        .getId(), commandRequest.getPage());
  }

  private NotificationDataWebResponse toNotificationDataWebResponse(
      GetNotificationCommandRequest commandRequest) {

    CustomPage<NotificationItem> notificationPage = getNotificationByUserId(commandRequest);
    List<NotificationDataWebResponse.NotificationData> notifications = notificationPage.getContent()
        .stream()
        .map(notification -> NotificationDataWebResponse.NotificationData.builder()
            .id(notification.getId())
            .createdAt(notification.getCreatedAt())
            .title(notification.getTitle())
            .description(notification.getDescription())
            .redirectionUrl(notification.getRedirectionUrl())
            .isOpened(notification.isOpened())
            .build())
        .collect(Collectors.toList());
    PaginationData paginationData = PaginationData.builder()
        .currentPage(notificationPage.getPageNumber())
        .totalPage(notificationPage.getTotalPages())
        .build();

    return NotificationDataWebResponse.builder()
        .paginationData(paginationData)
        .notifications(notifications)
        .build();
  }
}

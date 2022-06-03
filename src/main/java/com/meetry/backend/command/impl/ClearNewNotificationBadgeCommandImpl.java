package com.meetry.backend.command.impl;

import com.meetry.backend.command.ClearNewNotificationBadgeCommand;
import com.meetry.backend.command.model.ClearNewNotificationBadgeCommandRequest;
import com.meetry.backend.repository.NotificationRepository;
import com.meetry.backend.web.model.request.RealtimeNotificationWebSocketPayload;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClearNewNotificationBadgeCommandImpl implements ClearNewNotificationBadgeCommand {

  private final NotificationRepository notificationRepository;

  private final SimpMessagingTemplate simpMessagingTemplate;

  @Override
  public Void execute(ClearNewNotificationBadgeCommandRequest commandRequest) {

    notificationRepository.clearNewNotificationBadge(commandRequest.getUserId());

    RealtimeNotificationWebSocketPayload payload =
        new RealtimeNotificationWebSocketPayload(false);
    simpMessagingTemplate.convertAndSend(String.format("/notification/%s", commandRequest.getUserId()), payload);

    return null;
  }
}

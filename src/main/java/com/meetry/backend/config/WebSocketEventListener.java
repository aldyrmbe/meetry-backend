package com.meetry.backend.config;

import com.meetry.backend.command.CommandExecutor;
import com.meetry.backend.entity.notifikasi.Notifikasi;
import com.meetry.backend.repository.NotificationRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.request.RealtimeNotificationWebSocketPayload;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Objects;

@Component
@AllArgsConstructor
public class WebSocketEventListener {

  private final SimpMessagingTemplate simpMessagingTemplate;

  private final NotificationRepository notificationRepository;

  private final CommandExecutor commandExecutor;

  @EventListener
  public void handleInitialNotificationValue(SessionSubscribeEvent event) {

    GenericMessage message = (GenericMessage) event.getMessage();
    String simpDestination = (String) message.getHeaders()
        .get("simpDestination");

    if (Objects.nonNull(simpDestination) && simpDestination.startsWith("/notification")) {
      String[] split = simpDestination.split("/");
      String userId = split[split.length - 1];

      RealtimeNotificationWebSocketPayload payload =
          new RealtimeNotificationWebSocketPayload(hasLatestNotification(userId));
      simpMessagingTemplate.convertAndSend(simpDestination, payload);
    }
  }

  private boolean hasLatestNotification(String userId) {

    Notifikasi notifikasi = notificationRepository.findById(userId)
        .orElseThrow(() -> new BaseException("Notifikasi tidak blbleldeflef"));

    return notifikasi.isHasNewNotification();
  }
}

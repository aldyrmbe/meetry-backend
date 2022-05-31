package com.meetry.backend.web;

import com.meetry.backend.entity.HasNewNotification;
import com.meetry.backend.repository.HasNewNotificationRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.request.RealtimeNotificationWebSocketPayload;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final HasNewNotificationRepository hasNewNotificationRepository;

  @MessageMapping("/clearNotification/{id}")
  public void clearNewNotification(@DestinationVariable("id") String id){
      HasNewNotification hasNewNotification = hasNewNotificationRepository.findById(id)
          .orElseThrow(() -> new BaseException("Not found."));
      hasNewNotification.setHasNewNotification(false);
      hasNewNotificationRepository.save(hasNewNotification);

      RealtimeNotificationWebSocketPayload payload = new RealtimeNotificationWebSocketPayload(false);
      simpMessagingTemplate.convertAndSend(String.format("/notification/%s", id), payload);
  }


}

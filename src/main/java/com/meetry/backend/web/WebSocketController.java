package com.meetry.backend.web;

import com.meetry.backend.command.ClearNewNotificationBadgeCommand;
import com.meetry.backend.command.CommandExecutor;
import com.meetry.backend.command.model.ClearNewNotificationBadgeCommandRequest;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

  private final CommandExecutor commandExecutor;

  @MessageMapping("/clearNotification/{userId}")
  public void clearNewNotificationBadge(@DestinationVariable("userId") String userId) {

    ClearNewNotificationBadgeCommandRequest commandRequest = ClearNewNotificationBadgeCommandRequest.builder()
        .userId(userId)
        .build();

    commandExecutor.execute(ClearNewNotificationBadgeCommand.class, commandRequest);
  }

}

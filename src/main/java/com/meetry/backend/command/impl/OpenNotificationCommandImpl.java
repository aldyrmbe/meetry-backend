package com.meetry.backend.command.impl;

import com.meetry.backend.command.OpenNotificationCommand;
import com.meetry.backend.command.model.OpenNotificationCommandRequest;
import com.meetry.backend.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OpenNotificationCommandImpl implements OpenNotificationCommand {

    private final NotificationRepository notificationRepository;

    @Override
    public Void execute(OpenNotificationCommandRequest commandRequest) {

        notificationRepository.openNotification(commandRequest.getUserId(), commandRequest.getNotificationId());

        return null;
    }
}

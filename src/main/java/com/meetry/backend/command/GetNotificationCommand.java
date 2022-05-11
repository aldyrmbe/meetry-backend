package com.meetry.backend.command;

import com.meetry.backend.command.model.GetNotificationCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.NotificationDataWebResponse;

public interface GetNotificationCommand
    extends Command<GetNotificationCommandRequest, DefaultResponse<NotificationDataWebResponse>> {
}

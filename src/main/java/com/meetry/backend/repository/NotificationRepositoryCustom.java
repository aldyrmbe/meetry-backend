package com.meetry.backend.repository;

import com.meetry.backend.entity.CustomPage;
import com.meetry.backend.entity.notifikasi.NotificationItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationRepositoryCustom {
    void saveNotification(String userId, NotificationItem notificationItem);
    CustomPage<NotificationItem> getNotificationPage(String userId, int page);
    void clearNewNotificationBadge(String userId);
    void openNotification(String userId, String notificationId);
}

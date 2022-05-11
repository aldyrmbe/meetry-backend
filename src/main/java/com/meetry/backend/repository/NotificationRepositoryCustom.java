package com.meetry.backend.repository;

import com.meetry.backend.entity.notifikasi.Notifikasi;

import java.util.List;

public interface NotificationRepositoryCustom {
    List<Notifikasi> getNotification(String id, int page);
}

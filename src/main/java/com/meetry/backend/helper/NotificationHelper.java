package com.meetry.backend.helper;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.proyek.Proyek;

import java.util.List;

public interface NotificationHelper {
    void sendNotificationForProyekOnDiscussion(Proyek proyek); // Notifikasi ketika proyek sudah dapat mitra / peneliti
    void sendNotificationForProyekOnActive(Proyek proyek); // Notifikasi ketika proyek sudah aktif
    void sendNotificationForClosingProyek(Proyek proyek); // Notifikasi ketika proyek ditutup (selesai/batal)
    void sendNotificationOnNewComment(String proyekId, String commentatorId, String commentatorName, String folderId, String subFolderId, String subFolderName);
}

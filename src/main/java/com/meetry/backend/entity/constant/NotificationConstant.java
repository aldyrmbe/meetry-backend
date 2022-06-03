package com.meetry.backend.entity.constant;

public class NotificationConstant {

  public static String GENERAL_REDIRECTION_URL =
      "/%s/kolaborasi?searchQuery=%s&proyekId=%s";

  public static String NEW_COMMENT_NOTIFICATION_REDIRECTION_URL =
      "/%s/kolaborasi?searchQuery=%s&tabIndex=2&proyekId=%s&folderId=%s&subFolderId=%s&subFolderName=%s";

  public static String ON_DISCUSSION_TITLE_PEMOHON_PENELITI = "Kami berhasil menemukan mitra untuk Anda";

  public static String ON_DISCUSSION_DESC_PEMOHON_PENELITI =
      "Mitra Anda adalah %s. Silakan bergabung ke dalam group chat WhatsApp berikut ini untuk berdiskusi dengan mitra: %s. Account Officer kami (%s) akan membantu mencatat dan menunjang proses diskusi Anda bersama mitra.";

  public static String ON_DISCUSSION_TITLE_PEMOHON_MITRA = "Kami berhasil menemukan peneliti untuk Anda";

  public static String ON_DISCUSSION_DESC_PEMOHON_MITRA =
      "Peneliti yang mendukung proyek Anda adalah %s. Silakan bergabung ke dalam group chat WhatsApp berikut ini untuk berdiskusi dengan mitra: %s. Account Officer kami (%s) akan membantu mencatat dan menunjang proses diskusi Anda bersama mitra.";

  public static String ON_ACTIVE_TITLE_RECEIVER_PENELITI = "Selamat! Anda dan mitra sepakat untuk melanjutkan kolaborasi";

  public static String ON_ACTIVE_TITLE_RECEIVER_MITRA = "Selamat! Anda dan peneliti sepakat untuk melanjutkan kolaborasi";

  public static String ON_ACTIVE_DESC =
      "Proyek Anda yang berjudul \"%s\" sudah aktif. Silakan buka halaman “Kolaborasi Saya” untuk melanjutkan aktivitas kemitraan.";

  public static String ON_CLOSE_TITLE = "Proyek Anda telah ditutup";

  public static String ON_CLOSE_DESC =
      "Proyek Anda yang berjudul \"%s\" sudah ditutup oleh Account Officer. Lihat detailnya di halaman “Kolaborasi Saya”. Jika mengalami kendala, silakan hubungi kami.";

  public static String NEW_COMMENT_TITLE = "Ada komentar baru di Logbook";

  public static String NEW_COMMENT_DESC =
      "%s menambahkan komentar baru di proyek yang berjudul \"%s\". Klik untuk melihat komentar tersebut.";
}

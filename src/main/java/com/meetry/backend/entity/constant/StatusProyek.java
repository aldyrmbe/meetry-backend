package com.meetry.backend.entity.constant;

public enum StatusProyek {
    DALAM_PENGAJUAN, // Request proyek
    DALAM_DISKUSI, // Mitra / Peneliti sesuai request ditemukan
    AKTIF, // Proyek diresmikan
    SELESAI, // Proyek selesai
    DIBATALKAN // Proyek dibatalkan
}

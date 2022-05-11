package com.meetry.backend.helper;

import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.Proyek;

import java.util.List;

public interface ProyekHelper {
    Proyek findProyekById(String proyekId);
    void verifyProyekStatusForLogbookOperations(Proyek proyek);
    void verifyProyekStatusToCloseProyek(Proyek proyek);
    void verifyProyekStatusToCancelProyek(Proyek proyek);
    String getFormattedNames(List<String> names);
}

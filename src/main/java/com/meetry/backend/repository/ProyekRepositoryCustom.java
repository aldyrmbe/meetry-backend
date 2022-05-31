package com.meetry.backend.repository;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.File;
import com.meetry.backend.entity.proyek.Proyek;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProyekRepositoryCustom {
    Page<Proyek> getProyekList(Session session, StatusProyek status, String searchQuery, int page);
    Page<Proyek> getProyekOnRequest(Role pemohon, String searchQuery, int page);
    void saveFiles(String proyekId, List<File> files);
}

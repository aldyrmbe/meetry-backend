package com.meetry.backend.repository;

import com.meetry.backend.entity.user.Mitra;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MitraRepositoryCustom {
    List<Mitra> getMitraByName(String name);
    Page<Mitra> searchMitraByName(String name, int page);
}

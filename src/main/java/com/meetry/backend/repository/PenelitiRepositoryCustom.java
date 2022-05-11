package com.meetry.backend.repository;

import com.meetry.backend.entity.user.Peneliti;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PenelitiRepositoryCustom {
    List<Peneliti> getPenelitiByName(String name);
    Page<Peneliti> searchPenelitiByName(String name, int page);
}

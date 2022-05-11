package com.meetry.backend.repository;

import com.meetry.backend.entity.Universitas;

import java.util.List;

public interface UniversitasRepositoryCustom {
    List<Universitas> getUniversitasByName(String name);
}

package com.meetry.backend.repository;

import com.meetry.backend.entity.Universitas;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniversitasRepository extends MongoRepository<Universitas, String>, UniversitasRepositoryCustom {
}

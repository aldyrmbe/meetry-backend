package com.meetry.backend.repository.user;

import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.repository.MitraRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MitraRepository extends MongoRepository<Mitra, String>, MitraRepositoryCustom {
    Optional<Mitra> findByEmail(String email);
}

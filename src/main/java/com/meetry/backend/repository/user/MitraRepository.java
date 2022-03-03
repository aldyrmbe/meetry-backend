package com.meetry.backend.repository.user;

import com.meetry.backend.entity.user.Mitra;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MitraRepository extends MongoRepository<Mitra, String> {
    Optional<Mitra> findByEmail(String email);
}

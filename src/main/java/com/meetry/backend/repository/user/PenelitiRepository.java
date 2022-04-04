package com.meetry.backend.repository.user;

import com.meetry.backend.entity.user.Peneliti;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PenelitiRepository extends MongoRepository<Peneliti, String> {
    Optional<Peneliti> findByEmail(String email);
}

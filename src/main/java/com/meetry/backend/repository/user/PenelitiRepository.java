package com.meetry.backend.repository.user;

import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.repository.PenelitiRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PenelitiRepository extends MongoRepository<Peneliti, String>, PenelitiRepositoryCustom {
    Optional<Peneliti> findByEmail(String email);
}

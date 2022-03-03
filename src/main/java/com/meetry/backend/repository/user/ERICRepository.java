package com.meetry.backend.repository.user;

import com.meetry.backend.entity.user.ERIC;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ERICRepository extends MongoRepository<ERIC, String> {
    Optional<ERIC> findByEmail(String email);
}

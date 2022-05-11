package com.meetry.backend.repository;

import com.meetry.backend.entity.komentar.Komentar;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface KomentarRepository extends MongoRepository<Komentar, String> {
    List<Komentar> findAllByLogbookIdOrderByCreatedAtAsc(String logbookId);
}

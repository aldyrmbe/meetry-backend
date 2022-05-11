package com.meetry.backend.repository;

import com.meetry.backend.entity.logbook.Logbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogbookRepository extends MongoRepository<Logbook, String> {
    Page<Logbook> findAllBySubFolderIdOrderByCreatedAtDesc(String subFolderId, Pageable pageable);
}

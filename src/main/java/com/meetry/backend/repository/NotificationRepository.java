package com.meetry.backend.repository;

import com.meetry.backend.entity.notifikasi.Notifikasi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notifikasi, String>, NotificationRepositoryCustom {
    Page<Notifikasi> getAllByReceiverContainingOrderByCreatedAtDesc(String id, Pageable pageable);
}

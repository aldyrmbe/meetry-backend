package com.meetry.backend.repository;

import com.meetry.backend.entity.HasNewNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasNewNotificationRepository
    extends MongoRepository<HasNewNotification, String>, HasNewNotificationRepositoryCustom {

}

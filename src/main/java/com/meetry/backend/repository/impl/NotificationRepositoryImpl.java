package com.meetry.backend.repository.impl;

import com.meetry.backend.entity.notifikasi.Notifikasi;
import com.meetry.backend.repository.NotificationRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Notifikasi> getNotification(String id, int page) {

        Query query = new Query();
        Criteria criteria = Criteria.where("receiver").is(id);
        query.addCriteria(criteria);
        query.with(PageRequest.of(page, 5));
        query.with(Sort.by("createdAt").descending());

        return mongoTemplate.find(query, Notifikasi.class);
    }
}

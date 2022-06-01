package com.meetry.backend.repository.impl;

import com.meetry.backend.entity.HasNewNotification;
import com.meetry.backend.repository.HasNewNotificationRepositoryCustom;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class HasNewNotificationRepositoryImpl implements HasNewNotificationRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public void openNotification(String userId, String notificationId) {

    Query query = new Query();
    Criteria criteria = Criteria.where("_id")
        .is(userId);

    query.addCriteria(criteria);

    Update update = new Update();
    update.pull("unopenedNotificationIds", notificationId);

    UpdateResult updateResult = mongoTemplate.updateFirst(query, update, HasNewNotification.class);
    System.out.println(updateResult.wasAcknowledged());
  }


}

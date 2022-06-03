package com.meetry.backend.repository.impl;

import com.meetry.backend.entity.CustomPage;
import com.meetry.backend.entity.notifikasi.NotificationItem;
import com.meetry.backend.entity.notifikasi.Notifikasi;
import com.meetry.backend.repository.NotificationRepositoryCustom;
import com.meetry.backend.web.exception.BaseException;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  private static final int ITEM_PER_PAGE = 5;

  @Override
  public void saveNotification(String userId, NotificationItem notificationItem) {

    Query query = new Query();
    Criteria criteria = Criteria.where("_id")
        .is(userId);
    query.addCriteria(criteria);

    Update update = new Update();
    update.push("items", notificationItem);
    update.set("hasNewNotification", true);

    mongoTemplate.updateFirst(query, update, Notifikasi.class);
  }

  @Override
  public CustomPage<NotificationItem> getNotificationPage(String userId, int page) {

    Notifikasi notifikasi = mongoTemplate.findById(userId, Notifikasi.class);

    if (Objects.isNull(notifikasi))
      throw new BaseException("Notifikasi tidak ditemukan.");

    List<NotificationItem> content = notifikasi.getItems()
        .stream()
        .sorted(Comparator.comparing(NotificationItem::getCreatedAt).reversed())
        .collect(Collectors.toList());

    List<List<NotificationItem>> pages = getPages(content);
    List<NotificationItem> pageContent = pages.isEmpty() ? Collections.emptyList() : pages.get(page);
    int totalPages = pages.isEmpty() ? 0 : pages.size() - 1;
    return CustomPage.<NotificationItem>builder()
        .content(pageContent)
        .pageNumber(page)
        .totalPages(totalPages)
        .build();
  }

  @Override
  public void clearNewNotificationBadge(String userId) {

    Query query = new Query();
    Criteria criteria = Criteria.where("_id").is(userId);
    query.addCriteria(criteria);

    Update update = new Update();
    update.set("hasNewNotification", false);

    mongoTemplate.updateFirst(query, update, Notifikasi.class);
  }

  @Override
  public void openNotification(String userId, String notificationId) {

    Query query = new Query();
    Criteria userIdCriteria = Criteria.where("_id").is(userId);
    Criteria notificationIdCriteria = Criteria.where("items._id").is(notificationId);
    query.addCriteria(userIdCriteria).addCriteria(notificationIdCriteria);

    Update update = new Update();
    update.set("items.$.isOpened", true);

    mongoTemplate.updateFirst(query, update, Notifikasi.class);
  }

  private <T> List<List<T>> getPages(Collection<T> content){
    if(Objects.isNull(content)){
      return Collections.emptyList();
    }

    if(content.isEmpty()){
      return Collections.emptyList();
    }

    List<T> list = new ArrayList<T>(content);
    int pageSize = 5;
    int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);

    List<List<T>> pages = new ArrayList<>(numPages);
    for(int pageNum=0; pageNum < numPages;){
      pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
    }

    return pages;
  }
}

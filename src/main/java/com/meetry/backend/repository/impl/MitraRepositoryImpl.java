package com.meetry.backend.repository.impl;

import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.repository.MitraRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MitraRepositoryImpl implements MitraRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  private static final int ITEM_PER_PAGE = 5;

  @Override
  public List<Mitra> getMitraByName(String name) {

    String searchQueryRegex = String.format(".*%s.*", name);
    Query query = new Query();
    query.addCriteria(Criteria.where("namaPerusahaan")
        .regex(searchQueryRegex, "i"));

    return mongoTemplate.find(query, Mitra.class);
  }

  @Override
  public Page<Mitra> searchMitraByName(String name, int page) {

    String searchQueryRegex = String.format(".*%s.*", name);

    Query query = new Query();
    Query countQuery = new Query();

    Criteria nameCriteria = Criteria.where("namaPerusahaan")
        .regex(searchQueryRegex, "i");
    query.addCriteria(nameCriteria);
    countQuery.addCriteria(nameCriteria);

    Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
    query.with(pageable);

    return PageableExecutionUtils.getPage(mongoTemplate.find(query, Mitra.class), pageable,
        () -> mongoTemplate.count(countQuery, Mitra.class));
  }
}

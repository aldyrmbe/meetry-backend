package com.meetry.backend.repository.impl;

import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.repository.AccountOfficerRepositoryCustom;
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
public class AccountOfficerRepositoryImpl implements AccountOfficerRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  private static final int ITEM_PER_PAGE = 5;

  @Override
  public List<AccountOfficer> getAccountOfficerByName(String name) {

    String searchQueryRegex = String.format(".*%s.*", name);
    Query query = new Query();
    query.addCriteria(Criteria.where("name")
        .regex(searchQueryRegex, "i"));

    return mongoTemplate.find(query, AccountOfficer.class);
  }

  @Override
    public Page<AccountOfficer> searchAccountOfficerByName(String name, int page) {
        String searchQueryRegex = String.format(".*%s.*", name);
        Query query = new Query();
        Query countQuery = new Query();
        Criteria nameCriteria = Criteria.where("name").regex(searchQueryRegex, "i");
        
        query.addCriteria(nameCriteria);
        countQuery.addCriteria(nameCriteria);

        Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
        query.with(pageable);

      return PageableExecutionUtils.getPage(mongoTemplate.find(query, AccountOfficer.class), pageable,
          () -> mongoTemplate.count(countQuery, AccountOfficer.class));
    }
}

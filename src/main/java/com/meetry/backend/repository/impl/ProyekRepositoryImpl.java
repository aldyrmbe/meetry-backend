package com.meetry.backend.repository.impl;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.File;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.repository.ProyekRepositoryCustom;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class ProyekRepositoryImpl implements ProyekRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  private static final int ITEM_PER_PAGE = 5;

  @Override
  public Page<Proyek> getProyekList(Session session, StatusProyek status, String searchQuery, int page) {

    Query query = new Query();
    Query countQuery = new Query();

    if(session.getRole().equals(Role.ERIC)){
      Criteria criteria = Criteria.where("status").ne(StatusProyek.DALAM_PENGAJUAN);
      query.addCriteria(criteria);
      countQuery.addCriteria(criteria);
    }

    if (Objects.nonNull(status)) {
      Criteria criteria = Criteria.where("status")
          .is(status);
      query.addCriteria(criteria);
      countQuery.addCriteria(criteria);
    }

    if (Role.PENELITI.equals(session.getRole())) {
      Criteria checkPenelitiAuthorization = Criteria.where("partisipan.peneliti")
          .in(session.getId());
      query.addCriteria(checkPenelitiAuthorization);
      countQuery.addCriteria(checkPenelitiAuthorization);
    }

    if (Role.MITRA.equals(session.getRole())) {
      Criteria checkMitraAuthorization = Criteria.where("partisipan.mitra")
          .in(session.getId());
      query.addCriteria(checkMitraAuthorization);
      countQuery.addCriteria(checkMitraAuthorization);
    }

    if (Role.ACCOUNT_OFFICER.equals(session.getRole())) {
      Criteria checkAccountOfficerAuthorization = Criteria.where("partisipan.accountOfficer")
          .is(session.getId());
      query.addCriteria(checkAccountOfficerAuthorization);
      countQuery.addCriteria(checkAccountOfficerAuthorization);
    }

    if (Objects.nonNull(searchQuery)) {
      String searchQueryRegex = String.format(".*%s.*", searchQuery);
      Criteria searchCriteria = Criteria.where("judulProyek")
          .regex(searchQueryRegex, "i");
      query.addCriteria(searchCriteria);
      countQuery.addCriteria(searchCriteria);
    }

    Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
    query.with(pageable);
    query.with(Sort.by("createdAt")
        .descending());

    return PageableExecutionUtils.getPage(mongoTemplate.find(query, Proyek.class), pageable,
        () -> mongoTemplate.count(countQuery, Proyek.class));
  }

  @Override
  public Page<Proyek> getProyekOnRequest(Role pemohon, String searchQuery, int page) {
    Query query = new Query();
    Query countQuery = new Query();

    Criteria pemohonCriteria = Criteria.where("pemohon").is(pemohon);
    Criteria statusCriteria = Criteria.where("status").is(StatusProyek.DALAM_PENGAJUAN);

    query.addCriteria(pemohonCriteria);
    query.addCriteria(statusCriteria);
    countQuery.addCriteria(pemohonCriteria);
    countQuery.addCriteria(statusCriteria);

    if (Objects.nonNull(searchQuery)) {
      String searchQueryRegex = String.format(".*%s.*", searchQuery);
      Criteria searchCriteria = Criteria.where("judulProyek")
          .regex(searchQueryRegex, "i");
      query.addCriteria(searchCriteria);
      countQuery.addCriteria(searchCriteria);
    }

    Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
    query.with(pageable);
    query.with(Sort.by("createdAt")
        .descending());

    return PageableExecutionUtils.getPage(mongoTemplate.find(query, Proyek.class), pageable,
        () -> mongoTemplate.count(countQuery, Proyek.class));
  }

  @Override
  public void saveFiles(String proyekId, List<File> files) {
    Query query = new Query();
    Criteria criteria = Criteria.where("_id").is(proyekId);
    query.addCriteria(criteria);

    Update filesToUpdate = new Update();
    filesToUpdate.push("files").each(files.toArray());

    UpdateResult updateResult = mongoTemplate.updateFirst(query, filesToUpdate, Proyek.class);
    System.out.println(updateResult.wasAcknowledged());
  }
}

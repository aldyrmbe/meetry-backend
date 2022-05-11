package com.meetry.backend.repository.impl;

import com.meetry.backend.entity.Universitas;
import com.meetry.backend.repository.UniversitasRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UniversitasRepositoryImpl implements UniversitasRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Universitas> getUniversitasByName(String name) {

        Query query = new Query();
        String searchQueryRegex = String.format(".*%s.*", name);
        query.addCriteria(Criteria.where("name").regex(searchQueryRegex, "i"));

        return mongoTemplate.find(query, Universitas.class);
    }
}

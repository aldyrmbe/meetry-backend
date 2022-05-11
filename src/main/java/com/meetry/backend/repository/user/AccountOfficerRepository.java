package com.meetry.backend.repository.user;

import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.repository.AccountOfficerRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountOfficerRepository extends MongoRepository<AccountOfficer, String>, AccountOfficerRepositoryCustom {
    Optional<AccountOfficer> findByEmail(String email);
}

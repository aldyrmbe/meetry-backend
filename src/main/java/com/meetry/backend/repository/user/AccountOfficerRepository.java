package com.meetry.backend.repository.user;

import com.meetry.backend.entity.user.AccountOfficer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountOfficerRepository extends MongoRepository<AccountOfficer, String> {
    Optional<AccountOfficer> findByEmail(String email);
}

package com.meetry.backend.repository;

import com.meetry.backend.entity.user.AccountOfficer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountOfficerRepositoryCustom {
    List<AccountOfficer> getAccountOfficerByName(String name);
    Page<AccountOfficer> searchAccountOfficerByName(String name, int page);
}

package com.meetry.backend.repository;

import com.meetry.backend.entity.subfolder.SubFolder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubFolderRepository extends MongoRepository<SubFolder, String> {
    List<SubFolder> findAllByFolderId(String folderId);
}

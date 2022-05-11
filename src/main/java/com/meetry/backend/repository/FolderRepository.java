package com.meetry.backend.repository;

import com.meetry.backend.entity.folder.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FolderRepository extends MongoRepository<Folder, String> {
    List<Folder> findAllByProyekId(String proyekId);
}

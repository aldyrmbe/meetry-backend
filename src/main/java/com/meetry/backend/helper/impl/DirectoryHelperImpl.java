package com.meetry.backend.helper.impl;

import com.meetry.backend.entity.subfolder.SubFolder;
import com.meetry.backend.helper.DirectoryHelper;
import com.meetry.backend.repository.SubFolderRepository;
import com.meetry.backend.web.exception.BaseException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DirectoryHelperImpl implements DirectoryHelper {

    private final SubFolderRepository subFolderRepository;

    @Override
    public void checkSubFolderNameAvailability(String folderId, String subFolderName) {
        List<SubFolder> subFolders = subFolderRepository.findAllByFolderId(folderId);
        List<String> subFolderNames = subFolders.stream()
            .map(SubFolder::getSubFolderName)
            .collect(Collectors.toList());

        if(subFolderNames.contains(subFolderName))
            throw new BaseException("Nama subfolder sudah ada. Silakan gunakan nama yang lain.");
    }
}

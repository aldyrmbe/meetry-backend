package com.meetry.backend.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubFoldersWebResponse {
    private String folderName;
    private List<SubFolder> subFolders;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubFolder {
        private String id;
        private String namaSubFolder;
    }
}

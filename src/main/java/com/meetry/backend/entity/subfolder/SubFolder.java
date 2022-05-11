package com.meetry.backend.entity.subfolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subfolder")
public class SubFolder {

    @Id
    private String id;

    @Field
    private String folderId;

    @Field
    private String subFolderName;
}

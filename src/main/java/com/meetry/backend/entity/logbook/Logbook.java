package com.meetry.backend.entity.logbook;

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
@Document(collection = "logbook")
public class Logbook {
    @Id
    private String id;

    @Field
    private Long createdAt;

    @Field
    private String senderId;

    @Field
    private String subFolderId;

    @Field
    private String title;

    @Field
    private Long activityTime;

    @Field
    private String content;

    @Field
    private List<String> tags;

}

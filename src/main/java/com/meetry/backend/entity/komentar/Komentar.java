package com.meetry.backend.entity.komentar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "komentar")
public class Komentar {

    @Id
    private String id;

    @Field
    private Long createdAt;

    @Field
    private String logbookId;

    @Field
    private String profilePhoto;

    @Field
    private String senderName;

    @Field
    private String content;
}

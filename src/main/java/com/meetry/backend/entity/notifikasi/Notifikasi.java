package com.meetry.backend.entity.notifikasi;

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
@Document(collection = "notifikasi")
public class Notifikasi {

    @Id
    private String id;

    @Field
    private Long createdAt;

    @Field
    private List<String> receiver;

    @Field
    private String title;

    @Field
    private String description;
}

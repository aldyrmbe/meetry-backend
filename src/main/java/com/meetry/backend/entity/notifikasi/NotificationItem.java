package com.meetry.backend.entity.notifikasi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationItem {

    private String id;

    @Field
    private boolean isOpened;

    @Field
    private Long createdAt;

    @Field
    private String title;

    @Field
    private String description;

    @Field
    private String redirectionUrl;
}

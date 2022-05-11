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
public class NotificationDataWebResponse {
    private List<NotificationData> notifications;
    private PaginationData paginationData;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NotificationData {
        private String id;
        private Long createdAt;
        private String title;
        private String description;
    }
}

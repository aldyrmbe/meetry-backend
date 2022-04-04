package com.meetry.backend.helper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImgBBUploadResponse {

    private DataResponse data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataResponse {
        private ImageResponse image;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ImageResponse {
            private String filename;
            private String url;
        }

    }
}

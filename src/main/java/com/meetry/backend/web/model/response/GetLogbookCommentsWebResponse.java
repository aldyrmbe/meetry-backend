package com.meetry.backend.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLogbookCommentsWebResponse {
    private String profilePhoto;
    private String pengirim;
    private Long waktu;
    private String isi;
}

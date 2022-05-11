package com.meetry.backend.command.model;

import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.StatusProyek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetListProyekCommandRequest {
    private Session session;
    private StatusProyek status;
    private String searchQuery;
    private int page;
}

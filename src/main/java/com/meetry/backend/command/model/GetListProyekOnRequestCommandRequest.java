package com.meetry.backend.command.model;

import com.meetry.backend.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetListProyekOnRequestCommandRequest {
    private Role pemohon;
    private String searchQuery;
    private int page;
}

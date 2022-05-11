package com.meetry.backend.command.model;

import com.meetry.backend.entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetProyekOnDiscussionCommandRequest {
    private Session session;
    private String proyekId;
    private List<String> partisipan;
    private String accountOfficer;
    private String whatsappGroupLink;
}

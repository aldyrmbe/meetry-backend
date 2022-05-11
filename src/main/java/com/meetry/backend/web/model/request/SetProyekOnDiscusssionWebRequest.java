package com.meetry.backend.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetProyekOnDiscusssionWebRequest {

    private List<String> partisipan;
    private String accountOfficer;
    private String whatsappGroupLink;
}

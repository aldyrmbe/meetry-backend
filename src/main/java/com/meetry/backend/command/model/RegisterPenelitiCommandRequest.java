package com.meetry.backend.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPenelitiCommandRequest {

    private String data;
    private MultipartFile fotoProfil;
}

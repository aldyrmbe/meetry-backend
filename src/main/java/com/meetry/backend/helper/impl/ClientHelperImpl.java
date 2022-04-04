package com.meetry.backend.helper.impl;

import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.model.ImgBBUploadResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@AllArgsConstructor
public class ClientHelperImpl implements ClientHelper {

    @SneakyThrows
    @Override
    public ImgBBUploadResponse uploadImage(MultipartFile image) {
        WebClient webClient = WebClient.create("https://api.imgbb.com/1/upload?key=ebe9f000b15674ca4087cf7b81d79d97");
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("image", new ByteArrayResource(image.getBytes())).filename(image.getName());
        return webClient.post()
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
            .retrieve()
            .bodyToMono(ImgBBUploadResponse.class)
            .timeout(Duration.ofSeconds(60L))
            .block();
    }
}

package com.meetry.backend.helper.impl;

import com.meetry.backend.helper.ClientHelper;
import com.meetry.backend.helper.model.GetPerguruanTinggiDataResponse;
import com.meetry.backend.helper.model.GoFileUploadResponse;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientHelperImpl implements ClientHelper {

  @SneakyThrows
  @Override
  public ImgBBUploadResponse uploadImage(MultipartFile image) {

    WebClient webClient = WebClient.create("https://api.imgbb.com/1/upload?key=ebe9f000b15674ca4087cf7b81d79d97");
    MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
    multipartBodyBuilder.part("image", new ByteArrayResource(image.getBytes()))
        .filename(image.getName());
    return webClient.post()
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
        .retrieve()
        .bodyToMono(ImgBBUploadResponse.class)
        .timeout(Duration.ofSeconds(60L))
        .block();
  }

  @SneakyThrows
  @Override
  public GoFileUploadResponse uploadFile(MultipartFile file) {

    WebClient webClient = WebClient.create("https://store4.gofile.io/uploadFile");
    MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
    multipartBodyBuilder.part("file", new ByteArrayResource(file.getBytes()))
        .filename(Optional.ofNullable(file.getOriginalFilename())
            .orElse(file.getName()));
    return webClient.post()
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
        .retrieve()
        .bodyToMono(GoFileUploadResponse.class)
        .timeout(Duration.ofSeconds(60L))
        .block();
  }

  // @Override
  // public GetPerguruanTinggiDataResponse getPerguruanTingi() {
  //
  // WebClient webClient = WebClient.create("https://api.ahmfarisi.com/perguruantinggi/");
  // return webClient.get()
  // .accept(MediaType.APPLICATION_JSON)
  // .retrieve()
  // .bodyToMono(GetPerguruanTinggiDataResponse.class)
  // .timeout(Duration.ofSeconds(60L))
  // .block();
  // }
  @Override
  public String getPerguruanTingi() {

    WebClient webClient = WebClient.create("https://api.ahmfarisi.com/perguruantinggi/");
    return webClient.mutate()
        .codecs(configurer -> configurer
            .defaultCodecs()
            .maxInMemorySize(16 * 1024 * 1024))
        .build()
        .get()
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(String.class)
        .timeout(Duration.ofSeconds(60L))
        .block();
  }
}

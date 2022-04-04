package com.meetry.backend.helper;

import com.meetry.backend.helper.model.ImgBBUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ClientHelper {
    ImgBBUploadResponse uploadImage(MultipartFile image);
}

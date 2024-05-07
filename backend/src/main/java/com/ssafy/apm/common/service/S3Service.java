package com.ssafy.apm.common.service;

import com.ssafy.apm.common.dto.S3FileResponseDto;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    S3FileResponseDto uploadFile(MultipartFile file) throws IOException;
    S3FileResponseDto uploadImage(MultipartFile file) throws IOException;
    S3FileResponseDto uploadVideo(MultipartFile file) throws IOException;
    String uploadBase64ImageToS3(String base64Image);
}

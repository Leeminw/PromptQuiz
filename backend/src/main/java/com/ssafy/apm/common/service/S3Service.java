package com.ssafy.apm.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    public String uploadFile(MultipartFile multipartFile) throws IOException;
}

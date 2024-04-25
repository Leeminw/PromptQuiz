package com.ssafy.apm.common.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.common.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData<?>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = s3Service.uploadFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(fileName)
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(fileDownloadUri));
    }
}

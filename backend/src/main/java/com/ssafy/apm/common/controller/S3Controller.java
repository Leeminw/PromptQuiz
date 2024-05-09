package com.ssafy.apm.common.controller;

import com.ssafy.apm.common.service.S3Service;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.common.dto.S3FileResponseDto;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/api/v1/file/upload")
    public ResponseEntity<ResponseData<?>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        S3FileResponseDto responseDto = s3Service.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PostMapping("/api/v1/image/upload")
    public ResponseEntity<ResponseData<?>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        S3FileResponseDto responseDto = s3Service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PostMapping("/api/v1/video/upload")
    public ResponseEntity<ResponseData<?>> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        S3FileResponseDto responseDto = s3Service.uploadVideo(file);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

//    @GetMapping("/download")
//    public ResponseEntity<Resource> downloadFile(@RequestParam("bucketName") String bucketName, @RequestParam("fileName") String fileName, HttpServletResponse response) {
//        try {
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//            s3Service.downloadFile(bucketName, fileName, response.getOutputStream());
//            return ResponseEntity.ok()
//                    .body(new InputStreamResource(response.getOutputStream()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

}

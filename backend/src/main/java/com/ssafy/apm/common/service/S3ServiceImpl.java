package com.ssafy.apm.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private static final String S3_BASE_URL = "https://%s.s3.amazonaws.com/";
    private static final String S3KEY_PREFIX = "images/";

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

//    public String uploadFileToS3(MultipartFile multipartFile) {
//        String s3Key = S3KEY_PREFIX + multipartFile.getOriginalFilename();
//        amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, videoFile));
//
//        // return uploaded file S3 URL
//        log.info("[uploadVideoToS3] Upload Video to S3 Server: {}", s3Key);
//        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, amazonS3.getRegionName(), s3Key);
//    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        return fileName;
    }

}

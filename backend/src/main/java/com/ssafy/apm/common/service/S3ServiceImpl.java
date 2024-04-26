package com.ssafy.apm.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.apm.common.dto.request.S3FileRequestDto;
import com.ssafy.apm.common.dto.response.S3FileResponseDto;
import com.ssafy.apm.common.repository.S3FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3FileRepository s3FileRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Override
    public S3FileResponseDto uploadFile(MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        String filename = "files/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        return uploadFileToS3(file, metadata, filename);
    }

    @Override
    public S3FileResponseDto uploadImage(MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        String filename = "images/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        return uploadFileToS3(file, metadata, filename);
    }

    @Override
    public S3FileResponseDto uploadVideo(MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        String filename = "videos/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        return uploadFileToS3(file, metadata, filename);
    }

    private S3FileResponseDto uploadFileToS3(MultipartFile file, ObjectMetadata metadata, String filename) throws IOException {
        amazonS3.putObject(bucketName, filename, file.getInputStream(), metadata);

        String url = String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
        S3FileRequestDto requestDto = S3FileRequestDto.builder()
                .originalFilename(file.getOriginalFilename())
                .filetype(file.getContentType())
                .filesize(file.getSize())
                .filename(filename)
                .url(url)
                .build();

        return new S3FileResponseDto(s3FileRepository.save(requestDto.toEntity()));
    }


//    public String uploadFileToS3(MultipartFile multipartFile) {
//        String s3Key = S3KEY_PREFIX + multipartFile.getOriginalFilename();
//        amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, videoFile));
//
//        // return uploaded file S3 URL
//        log.info("[uploadVideoToS3] Upload Video to S3 Server: {}", s3Key);
//        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, amazonS3.getRegionName(), s3Key);
//    }

//    @Override
//    public String uploadFile(MultipartFile file) throws IOException {
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
//        return fileName;
//    }

}

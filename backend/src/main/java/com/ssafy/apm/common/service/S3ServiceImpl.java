package com.ssafy.apm.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.apm.common.dto.S3FileRequestDto;
import com.ssafy.apm.common.dto.S3FileResponseDto;
import com.ssafy.apm.common.repository.S3FileRepository;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.io.IOException;
import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    private final S3FileRepository s3FileRepository;

    private final AmazonS3 amazonS3;

    @Override
    public S3FileResponseDto uploadFile(MultipartFile file) throws IOException {
        ObjectMetadata metadata = createMetadataFromMultipartFile(file);
        return uploadFileToS3(file, metadata, s3FileNameFormat("files",file));
    }

    @Override
    public S3FileResponseDto uploadImage(MultipartFile file) throws IOException {
        ObjectMetadata metadata = createMetadataFromMultipartFile(file);
        return uploadFileToS3(file, metadata, s3FileNameFormat("images",file));
    }

    @Override
    public S3FileResponseDto uploadVideo(MultipartFile file) throws IOException {
        ObjectMetadata metadata = createMetadataFromMultipartFile(file);
        return uploadFileToS3(file, metadata, s3FileNameFormat("videos",file));
    }

    public ObjectMetadata createMetadataFromMultipartFile(MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    public String s3FileNameFormat(String type, MultipartFile file){
        return String.format("%s/%s_%s", type, UUID.randomUUID(), file.getOriginalFilename());
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String uploadBase64ImageToS3(String base64Image) {
        ObjectMetadata metadata = new ObjectMetadata();
        byte[] imageBytes = Base64.decodeBase64(base64Image);
        String filename = String.format("images/%s.png", UUID.randomUUID());

        amazonS3.putObject(bucketName, filename, new ByteArrayInputStream(imageBytes), metadata);
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
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

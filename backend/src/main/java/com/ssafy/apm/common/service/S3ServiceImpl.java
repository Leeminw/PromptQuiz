package com.ssafy.apm.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.apm.common.dto.S3FileRequestDto;
import com.ssafy.apm.common.dto.S3FileResponseDto;
import com.ssafy.apm.common.repository.S3FileRepository;

import com.ssafy.apm.prompt.domain.Prompt;
import com.ssafy.apm.prompt.repository.PromptRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

//import org.springframework.scheduling.annotation.Scheduled;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final PromptRepository promptRepository;

    // @Scheduled(cron = "0 */1 * * * *", zone = "Asia/Seoul")
    private void backupS3() throws Exception {
        List<Prompt> prompts = promptRepository.findAll();
        for (Prompt prompt : prompts) {
            if (prompt.getUrl() == null) continue;

            String key  = new URL(prompt.getUrl()).getPath().substring(1);
            log.info("[Processing Backup] S3 key: " + key);

            // Check if exists downloads
            Path savePath = Paths.get("downloads").resolve(key).getParent();
            if (!Files.exists(savePath))
                Files.createDirectories(savePath);
            File filePath = savePath.resolve(Paths.get(key).getFileName().toString()).toFile();

            // Download file from S3
            try (InputStream inputStream = amazonS3.getObject(bucketName, key).getObjectContent();
                 FileOutputStream outputStream = new FileOutputStream(filePath)) {
                inputStream.transferTo(outputStream);
                log.info("File downloaded to: " + filePath.getAbsolutePath());

                String relativePath = "/downloads/" + filePath.getName();
                promptRepository.save(prompt.updateUrl(relativePath));
                log.info("Database Table Prompt url updated with: " + relativePath);
            } catch (Exception e) {
                log.error("Failed to download file", e);
                continue;
            }

            // Delete file from S3
            try {
                amazonS3.deleteObject(bucketName, key);
                log.info("File deleted from S3: " + key);
            } catch (Exception e) {
                log.error("Failed to delete file from S3", e);
            }
        }
    }

}

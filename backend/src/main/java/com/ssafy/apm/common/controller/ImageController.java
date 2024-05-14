package com.ssafy.apm.common.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.common.dto.ImageResponseDto;
import com.ssafy.apm.common.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/v1/images/upload")
    public ResponseEntity<ResponseData<?>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        ImageResponseDto responseDto = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/api/v1/images/download/{uuid}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String uuid) throws IOException {
        ByteArrayResource resource = imageService.downloadImage(uuid);
        ImageResponseDto responseDto = imageService.findImageByUuid(uuid);
        String filename = responseDto.getUuid() + ".png";

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(responseDto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping("/api/v1/images/{uuid}")
    public ResponseEntity<Resource> getImageSource(@PathVariable String uuid) throws IOException {
        InputStreamResource resource = imageService.getImageSource(uuid);
        ImageResponseDto responseDto = imageService.findImageByUuid(uuid);
        String filename = responseDto.getUuid() + ".png";

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(responseDto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PutMapping("/api/v1/images/update/{id}")
    public ResponseEntity<ResponseData<?>> updateImageById(@PathVariable Long id,
                                                           @RequestParam MultipartFile file) throws IOException {
        ImageResponseDto responseDto = imageService.updateImage(id, file);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping("/api/v1/images/update")
    public ResponseEntity<ResponseData<?>> updateImageByUuid(@RequestParam String uuid,
                                                             @RequestParam MultipartFile file) throws IOException {
        ImageResponseDto responseDto = imageService.updateImage(uuid, file);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DeleteMapping("/api/v1/images/delete/{id}")
    public ResponseEntity<ResponseData<?>> deleteImageById(@PathVariable Long id) throws IOException {
        ImageResponseDto responseDto = imageService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @DeleteMapping("/api/v1/images/delete")
    public ResponseEntity<ResponseData<?>> deleteImageByUuid(@RequestParam String uuid) throws IOException {
        ImageResponseDto responseDto = imageService.deleteImage(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/api/v1/images/info/{id}")
    public ResponseEntity<ResponseData<?>> findImageById(@PathVariable Long id) {
        ImageResponseDto responseDto = imageService.findImageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/api/v1/images/info")
    public ResponseEntity<ResponseData<?>> findImageByUuid(@RequestParam String uuid) {
        ImageResponseDto responseDto = imageService.findImageByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/api/v1/images/info/all")
    public ResponseEntity<ResponseData<?>> findAllImages() {
        List<ImageResponseDto> responseDto = imageService.findAllImages();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

}

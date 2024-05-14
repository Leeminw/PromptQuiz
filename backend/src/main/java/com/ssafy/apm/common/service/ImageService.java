package com.ssafy.apm.common.service;

import com.ssafy.apm.common.dto.ImageResponseDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    ImageResponseDto updateImage(Long id, MultipartFile file) throws IOException;
    ImageResponseDto updateImage(String uuid, MultipartFile file) throws IOException;
    ImageResponseDto deleteImage(Long id) throws IOException;
    ImageResponseDto deleteImage(String uuid) throws IOException;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ImageResponseDto findImageById(Long id);
    ImageResponseDto findImageByUuid(String uuid);
    List<ImageResponseDto> findAllImages();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ImageResponseDto uploadImage(MultipartFile file) throws IOException;
    ByteArrayResource downloadImage(String uuid) throws IOException;
    InputStreamResource getImageSource(String uuid) throws IOException;

}

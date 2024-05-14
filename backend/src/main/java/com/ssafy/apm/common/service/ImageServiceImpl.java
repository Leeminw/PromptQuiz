package com.ssafy.apm.common.service;

import com.ssafy.apm.common.domain.Image;
import com.ssafy.apm.common.dto.ImageRequestDto;
import com.ssafy.apm.common.dto.ImageResponseDto;
import com.ssafy.apm.common.exceptions.ImageNotFoundException;
import com.ssafy.apm.common.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final String imagePath = "/upload/images";

    @Override
    public ImageResponseDto updateImage(Long id, MultipartFile file) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException(id));
        Path filepath = Paths.get(image.getFilepath(), image.getUuid() + ".png");
        Files.write(filepath, file.getBytes());
        return new ImageResponseDto(imageRepository.save(image.update(file)));
    }

    @Override
    public ImageResponseDto updateImage(String uuid, MultipartFile file) throws IOException {
        Image image = imageRepository.findByUuid(uuid).orElseThrow(
                () -> new ImageNotFoundException(uuid));
        Path filepath = Paths.get(image.getFilepath(), image.getUuid() + ".png");
        Files.write(filepath, file.getBytes());
        return new ImageResponseDto(imageRepository.save(image.update(file)));
    }

    @Override
    public ImageResponseDto deleteImage(Long id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException(id));
        Path filepath = Paths.get(image.getFilepath(), image.getUuid() + ".png");
        Files.deleteIfExists(filepath);
        imageRepository.delete(image);
        return new ImageResponseDto(image);
    }

    @Override
    public ImageResponseDto deleteImage(String uuid) throws IOException {
        Image image = imageRepository.findByUuid(uuid).orElseThrow(
                () -> new ImageNotFoundException(uuid));
        Path filepath = Paths.get(image.getFilepath(), image.getUuid() + ".png");
        Files.deleteIfExists(filepath);
        imageRepository.delete(image);
        return new ImageResponseDto(image);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ImageResponseDto findImageById(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException(id));
        return new ImageResponseDto(image);
    }

    @Override
    public ImageResponseDto findImageByUuid(String uuid) {
        Image image = imageRepository.findByUuid(uuid).orElseThrow(
                () -> new ImageNotFoundException(uuid));
        return new ImageResponseDto(image);
    }

    @Override
    public List<ImageResponseDto> findAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(ImageResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ImageResponseDto uploadImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(imagePath);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);

        String uuid = UUID.randomUUID().toString();
        Path filepath = Paths.get(imagePath, uuid + ".png");
        Files.write(filepath, file.getBytes());

        ImageRequestDto requestDto = ImageRequestDto.builder()
                .url("https://k10a509.p.ssafy.io/api/v1/images/" + uuid + ".png")
                .uuid(uuid)
                .filepath(imagePath)
                .filesize(file.getSize())
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();
        return new ImageResponseDto(imageRepository.save(requestDto.toEntity()));
    }

    @Override
    public ByteArrayResource downloadImage(String uuid) throws IOException {
        Image image = imageRepository.findByUuid(uuid).orElseThrow(
                () -> new ImageNotFoundException(uuid));
        Path filepath = Paths.get(image.getFilepath(), image.getUuid() + ".png");
        byte[] data = Files.readAllBytes(filepath);
        return new ByteArrayResource(data);
    }

    @Override
    public InputStreamResource getImageSource(String uuid) throws IOException {
        Image image = imageRepository.findByUuid(uuid).orElseThrow(
                () -> new ImageNotFoundException(uuid));
        Path filepath = Paths.get(image.getFilepath(), image.getUuid() + ".png");
        return new InputStreamResource(Files.newInputStream(filepath));
    }

}

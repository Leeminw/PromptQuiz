package com.ssafy.apm.sdw.service;

import com.ssafy.apm.sdw.dto.SdwSimpleRequestDto;
import com.ssafy.apm.sdw.dto.SdwSimpleResponseDto;
import com.ssafy.apm.sdw.dto.SdwCustomRequestDto;
import com.ssafy.apm.sdw.dto.SdwCustomResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class SdwServiceImpl implements SdwService {

    @Value("${cloud.aws.gpu.server.ip}")
    private String gpuServerIP;

    @Override
    public SdwCustomResponseDto requestCustomStableDiffusion(SdwCustomRequestDto requestDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // String url = "http://127.0.0.1:7860/sdapi/v1/txt2img";
        String url = String.format("http://%s/sdapi/v1/txt2img", gpuServerIP);

        HttpEntity<SdwCustomRequestDto> request = new HttpEntity<>(requestDto, httpHeaders);
        try {
            log.info("[Request Stable Diffusion Create Images] {}", requestDto.toString());
            ResponseEntity<SdwCustomResponseDto> response = restTemplate.postForEntity(url, request, SdwCustomResponseDto.class);
            return Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No response body"));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error while creating images with prompt '{}': {}", requestDto, e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), "Error during image creation: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while creating images: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error during image creation");
        }
    }

    @Override
    public SdwSimpleResponseDto requestSimpleStableDiffusion(SdwSimpleRequestDto requestDto) {
        SdwCustomRequestDto sdwCustomRequestDto = new SdwCustomRequestDto();
        switch (requestDto.getStyle()) {
            case "Anime" -> sdwCustomRequestDto.updateAnimePrompt(requestDto.getPrompt());
            case "Disney" -> sdwCustomRequestDto.updateDisneyPrompt(requestDto.getPrompt());
            case "Realistic" -> sdwCustomRequestDto.updateRealisticPrompt(requestDto.getPrompt());
        }
        SdwCustomResponseDto sdwCustomResponseDto = requestCustomStableDiffusion(sdwCustomRequestDto);

        List<String> encodedImages = new ArrayList<>(sdwCustomResponseDto.getImages());
        List<byte[]> decodedImages = decodeImages(encodedImages);
        List<Resource> convertedImages = convertImages(decodedImages);
        SdwSimpleResponseDto responseDto = SdwSimpleResponseDto.builder()
                .style(requestDto.getStyle())
                .prompt(requestDto.getPrompt())
                .resources(convertedImages)
                .build();
        return responseDto;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<byte[]> decodeImages(List<String> base64Images) {
        List<byte[]> decodedImages = new ArrayList<>();
        for (String base64Image : base64Images) {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            decodedImages.add(imageBytes);
        }
        return decodedImages;
    }

    private List<Resource> convertImages(List<byte[]> decodedImages) {
        List<Resource> resources = new ArrayList<>();
        for (byte[] image : decodedImages)
            resources.add(new ByteArrayResource(image));
        return resources;
    }

    // 사용할지 말지 수정 필요
    private void saveImages(List<byte[]> decodedImages) throws IOException {
        Path outputPath = Paths.get("outputs");
        Files.createDirectories(outputPath);
        for (byte[] decodedImage : decodedImages) {
            String imageFileName = UUID.randomUUID() + ".jpg";
            Path imageFilePath = outputPath.resolve(imageFileName);
            Files.write(imageFilePath, decodedImage);
        }
    }

}

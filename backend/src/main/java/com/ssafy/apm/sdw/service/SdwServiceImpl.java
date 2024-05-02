package com.ssafy.apm.sdw.service;

import com.ssafy.apm.sdw.dto.DottegiRequestDto;
import com.ssafy.apm.sdw.dto.DottegiResponseDto;
import com.ssafy.apm.sdw.dto.SdwRequestDto;
import com.ssafy.apm.sdw.dto.SdwResponseDto;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public SdwResponseDto requestStableDiffusion(SdwRequestDto requestDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

//        String url = "http://127.0.0.1:7860/sdapi/v1/txt2img";
        String url = "http://222.107.238.44:7860/sdapi/v1/txt2img";

        HttpEntity<SdwRequestDto> request = new HttpEntity<>(requestDto, httpHeaders);
        try {
            log.info("[Request Stable Diffusion Create Images] {}", requestDto.toString());
            ResponseEntity<SdwResponseDto> response = restTemplate.postForEntity(url, request, SdwResponseDto.class);
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
    public DottegiResponseDto createDottegiTxt2Img(DottegiRequestDto requestDto) {
        SdwRequestDto sdwRequestDto = new SdwRequestDto();
        switch (requestDto.getStyle()) {
            case "Anime" -> sdwRequestDto.updateAnimePrompt(requestDto.getPrompt());
            case "Disney" -> sdwRequestDto.updateDisneyPrompt(requestDto.getPrompt());
            case "Realistic" -> sdwRequestDto.updateRealisticPrompt(requestDto.getPrompt());
        }
        SdwResponseDto sdwResponseDto = requestStableDiffusion(sdwRequestDto);

        List<String> encodedImages = new ArrayList<>(sdwResponseDto.getImages());
        List<byte[]> decodedImages = decodeImages(encodedImages);
        List<Resource> convertedImages = convertImages(decodedImages);
        DottegiResponseDto responseDto = DottegiResponseDto.builder()
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

package com.ssafy.apm.sdw.service;

import com.ssafy.apm.common.service.S3Service;
import com.ssafy.apm.prompt.domain.Prompt;
import com.ssafy.apm.prompt.repository.PromptRepository;
import com.ssafy.apm.sdw.dto.SdwCustomRequestDto;
import com.ssafy.apm.sdw.dto.SdwCustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoCreateServiceImpl implements AutoCreateService {

    private final PromptRepository promptRepository;
    private final SdwService sdwService;
    private final S3Service s3Service;

    private static List<Prompt> prompts;

    @Override
    //@Scheduled(cron = "0 */1 * * * *", zone = "Asia/Seoul")
    public void autoCreateScheduler() {
        prompts = promptRepository.findAll();
        for (Prompt prompt : prompts) {
            if (prompt.getUrl() == null) {
                String savedS3Url;
                SdwCustomResponseDto sdwCustomResponseDto;
                SdwCustomRequestDto sdwCustomRequestDto = new SdwCustomRequestDto();
                switch (prompt.getStyle()) {
                    case "anime" -> sdwCustomRequestDto.updateAnimePrompt(prompt.getEngSentence());
                    case "cartoon" -> sdwCustomRequestDto.updateDisneyPrompt(prompt.getEngSentence());
                    case "realistic" -> sdwCustomRequestDto.updateRealisticPrompt(prompt.getEngSentence());
                }

                sdwCustomResponseDto = sdwService.requestCustomStableDiffusion(sdwCustomRequestDto);
                savedS3Url = s3Service.uploadBase64ImageToS3(sdwCustomResponseDto.getImages().get(0));
                promptRepository.save(prompt.updateUrl(savedS3Url));
                log.info("[Stable-Diffusion-Webui-v1.8.0][POST][Request]: " +
                        prompt.getStyle() + " | " + prompt.getKorSentence());
                log.info("[Stable-Diffusion-Webui-v1.8.0][POST][Response]: " +
                        prompt.getStyle() + " | " + prompt.getKorSentence());
                log.info("[Amazon Web Service S3 Server][PUT][Saved S3 Url]: " + savedS3Url);
            }
        }
    }

}

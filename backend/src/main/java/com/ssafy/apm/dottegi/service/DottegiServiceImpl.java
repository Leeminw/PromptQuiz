package com.ssafy.apm.dottegi.service;

import com.ssafy.apm.common.dto.ImageResponseDto;
import com.ssafy.apm.common.service.ImageService;
import com.ssafy.apm.common.util.GoogleTranslator;
import com.ssafy.apm.dottegi.dto.DottegiResponseDto;
import com.ssafy.apm.sdw.dto.SdwCustomRequestDto;
import com.ssafy.apm.sdw.dto.SdwCustomResponseDto;
import com.ssafy.apm.sdw.service.SdwService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DottegiServiceImpl implements DottegiService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ImageService imageService;
    private final SdwService sdwService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Getter
    private volatile DottegiResponseDto lastUpdatedPayload;
    private final ConcurrentHashMap<String, Integer> countMessageStyles = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> countMessageSubjects = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> countMessageObjects = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> countMessageVerbs = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> countMessageSubAdjectives = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> countMessageObjAdjectives = new ConcurrentHashMap<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public DottegiResponseDto findLastUpdatedPayload() {
        lastUpdatedPayload.setCurUpdatedStyles(getTop10ListMap(countMessageStyles));
        lastUpdatedPayload.setCurUpdatedSubjects(getTop10ListMap(countMessageSubjects));
        lastUpdatedPayload.setCurUpdatedObjects(getTop10ListMap(countMessageObjects));
        lastUpdatedPayload.setCurUpdatedVerbs(getTop10ListMap(countMessageVerbs));
        lastUpdatedPayload.setCurUpdatedSubAdjectives(getTop10ListMap(countMessageSubAdjectives));
        lastUpdatedPayload.setCurUpdatedObjAdjectives(getTop10ListMap(countMessageObjAdjectives));
        return lastUpdatedPayload;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void processMessageStyle(String message) {
        countMessageStyles.merge(message, 1, Integer::sum);
        sendTop10CountMessages(countMessageStyles, "/ws/sub/dottegi/style");
    }

    @Override
    public void processMessageSubject(String message) {
        countMessageSubjects.merge(message, 1, Integer::sum);
        sendTop10CountMessages(countMessageSubjects, "/ws/sub/dottegi/subject");
    }

    @Override
    public void processMessageObject(String message) {
        countMessageObjects.merge(message, 1, Integer::sum);
        sendTop10CountMessages(countMessageObjects, "/ws/sub/dottegi/object");
    }

    @Override
    public void processMessageVerb(String message) {
        countMessageVerbs.merge(message, 1, Integer::sum);
        sendTop10CountMessages(countMessageVerbs, "/ws/sub/dottegi/verb");
    }

    @Override
    public void processMessageSubAdjective(String message) {
        countMessageSubAdjectives.merge(message, 1, Integer::sum);
        sendTop10CountMessages(countMessageSubAdjectives, "/ws/sub/dottegi/sub-adjective");
    }

    @Override
    public void processMessageObjAdjective(String message) {
        countMessageObjAdjectives.merge(message, 1, Integer::sum);
        sendTop10CountMessages(countMessageObjAdjectives, "/ws/sub/dottegi/obj-adjective");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendTop10CountMessages(ConcurrentHashMap<String, Integer> map, String destination) {
        List<Map<String, Integer>> top10 = getTop10Entries(map).stream()
                .map(entry -> Map.of(entry.getKey(), entry.getValue()))
                .toList();
        messagingTemplate.convertAndSend(destination, top10);
    }


    private List<Map.Entry<String, Integer>> getTop10Entries(ConcurrentHashMap<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .toList();
    }

    private List<Map<String, Integer>> getTop10ListMap(ConcurrentHashMap<String, Integer> map) {
        return getTop10Entries(map).stream()
                .map(entry -> Map.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    private String getTopWord(ConcurrentHashMap<String, Integer> map) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private long timerStart = System.currentTimeMillis();
    private final long timerDuration = 180000;
//    private final long timerDuration = 300000;

    @Scheduled(fixedRate = 1000)
    public void broadcastRemainingTime() throws IOException {
        long now = System.currentTimeMillis();
        long elapsed = now - timerStart;
        long remainingMillis = timerDuration - elapsed;

        if (remainingMillis <= 0) {
            timerStart = now;
            remainingMillis = timerDuration;
            sendLastUpdatedPayload();
        }

        long remainingSeconds = remainingMillis / 1000;
        String remainingTime = String.format("%02d:%02d",
                remainingSeconds / 60,
                remainingSeconds % 60);

        Map<String, Object> payload = new HashMap<>();
        payload.put("remainingTime", remainingTime);
        messagingTemplate.convertAndSend("/ws/sub/dottegi", payload);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendLastUpdatedPayload() throws IOException {
        /* DONE: 각각의 품사의 Top 10 문자열들을 불러온다.
            Top 1 단어들만 뽑아 합쳐 하나의 문자열을 만든다.
            Top 1 부터 10까지 각각의 품사들을 리스트에 담는다.
            Map을 초기화하고, /dottegi를 구독중인 유저들에게 합쳐진 하나의 문장과 리스트를 전송한다. */

        /* DONE: 예외 상황에 대한 처리 필요
            1. 최초 실행시 lastUpdatedPayload 가 Null 인 경우 기본 이미지 사용
            2. 기존값이 존재할 때, 새로운 값이 없다면 기존값 사용  */
        if (lastUpdatedPayload == null) {
            log.info("[No Data Exists] Send Basic Payload.");
            ImageResponseDto imageResponseDto = imageService.findImageByUuid("2c2e4dce-34c1-4f27-838a-6e1bc1bc00e9");
            lastUpdatedPayload = DottegiResponseDto.builder()
                    .lastUpdatedUrl(imageResponseDto.getUrl())
                    .lastUpdatedStyles(List.of(Map.of("Anime", 1)))
                    .lastUpdatedSubjects(List.of(Map.of("바람", 1)))
                    .lastUpdatedObjects(List.of(Map.of("벚꽃잎", 1)))
                    .lastUpdatedVerbs(List.of(Map.of("휘날리", 1)))
                    .lastUpdatedSubAdjectives(List.of(Map.of("부드러운", 1)))
                    .lastUpdatedObjAdjectives(List.of(Map.of("파스텔 톤의", 1)))
                    .build();
            return;
        }

        // 이전 값이 존재하며, 아무것도 바뀌지 않은 경우
        if (countMessageStyles.isEmpty() && countMessageVerbs.isEmpty() &&
                countMessageSubjects.isEmpty() && countMessageObjects.isEmpty() &&
                countMessageSubAdjectives.isEmpty() && countMessageObjAdjectives.isEmpty()) {
            log.info("[No Data Income] Send Last Updated Payload.");
            messagingTemplate.convertAndSend("/ws/sub/dottegi", lastUpdatedPayload);
            return;
        }

        // 이전 값이 존재하며, 스타일만 바뀐 경우
        if (!countMessageStyles.isEmpty() && countMessageVerbs.isEmpty() &&
                countMessageSubjects.isEmpty() && countMessageObjects.isEmpty() &&
                countMessageSubAdjectives.isEmpty() && countMessageObjAdjectives.isEmpty()) {
            log.info("[Data Income] Only Style Changed. Send Last Updated Payload.");
            for (Map<String, Integer> map : lastUpdatedPayload.getLastUpdatedSubAdjectives()) countMessageSubAdjectives.putAll(map);
            for (Map<String, Integer> map : lastUpdatedPayload.getLastUpdatedObjAdjectives()) countMessageObjAdjectives.putAll(map);
            for (Map<String, Integer> map : lastUpdatedPayload.getLastUpdatedSubjects()) countMessageSubjects.putAll(map);
            for (Map<String, Integer> map : lastUpdatedPayload.getLastUpdatedObjects()) countMessageObjects.putAll(map);
            for (Map<String, Integer> map : lastUpdatedPayload.getLastUpdatedVerbs()) countMessageVerbs.putAll(map);
        }
        // 이전 값이 존재하며, 스타일 이외의 값들이 바뀐 경우
        else if (countMessageStyles.isEmpty() && !countMessageVerbs.isEmpty() &&
                !countMessageSubjects.isEmpty() && !countMessageObjects.isEmpty() &&
                !countMessageSubAdjectives.isEmpty() && !countMessageObjAdjectives.isEmpty()) {
            log.info("[Data Income] Only Style Not Changed. Send Last Updated Payload.");
            for (Map<String, Integer> map : lastUpdatedPayload.getLastUpdatedStyles()) countMessageStyles.putAll(map);
        }
        for (int i = 0; i <= 50; i++)
            messagingTemplate.convertAndSend("/ws/sub/dottegi",
                    Map.of("remainingTime", "이미지 생성중... " + i + "%"));

        // Extract the top word from each category
        String curTopStyle = getTopWord(countMessageStyles);
        String curTopSubject = getTopWord(countMessageSubjects);
        String curTopObject = getTopWord(countMessageObjects);
        String curTopVerb = getTopWord(countMessageVerbs);
        String curTopSubAdjective = getTopWord(countMessageSubAdjectives);
        String curTopObjAdjective = getTopWord(countMessageObjAdjectives);

        // Combine the top words into a single sentence
        log.info("[Income Data Exists] Update Payload and Send.");
        String combinedSentence = String.format("%s %s이(가) %s %s을(를) %s하(고) 있다.",
                curTopSubAdjective, curTopSubject, curTopObjAdjective, curTopObject, curTopVerb);
        String prompt = GoogleTranslator.translate("ko", "en", combinedSentence);
        for (int i = 51; i <= 65; i++)
            messagingTemplate.convertAndSend("/ws/sub/dottegi",
                    Map.of("remainingTime", "이미지 생성중... " + i + "%"));

        /* DONE: 영문 번역하여 Stable-Diffusion 에 요청하여 Base64 Encoded Image 받아옴 */
        SdwCustomRequestDto sdwCustomRequestDto = new SdwCustomRequestDto();
        switch (curTopStyle) {
            case "Anime" -> sdwCustomRequestDto.updateAnimePrompt(prompt);
            case "Cartoon" -> sdwCustomRequestDto.updateDisneyPrompt(prompt);
            case "Realistic" -> sdwCustomRequestDto.updateRealisticPrompt(prompt);
        }
        SdwCustomResponseDto sdwCustomResponseDto = sdwService.requestCustomStableDiffusion(sdwCustomRequestDto);
        String base64Image = sdwCustomResponseDto.getImages().get(0);
        for (int i = 66; i <= 99; i++)
            messagingTemplate.convertAndSend("/ws/sub/dottegi",
                    Map.of("remainingTime", "이미지 생성중... " + i + "%"));

        /* DONE: RDB에 저장 or 파일로 저장하여 불러와서 Base64 인코딩 하여 전송하는 방식 */
        ImageResponseDto imageResponseDto = imageService.saveBase64Image(combinedSentence, base64Image);

        // Send the combined sentence and lists to subscribers
        DottegiResponseDto payload = DottegiResponseDto.builder()
                .lastUpdatedUrl(imageResponseDto.getUrl())
                .lastUpdatedVerbs(getTop10ListMap(countMessageVerbs))
                .lastUpdatedStyles(getTop10ListMap(countMessageStyles))
                .lastUpdatedObjects(getTop10ListMap(countMessageObjects))
                .lastUpdatedSubjects(getTop10ListMap(countMessageSubjects))
                .lastUpdatedSubAdjectives(getTop10ListMap(countMessageSubAdjectives))
                .lastUpdatedObjAdjectives(getTop10ListMap(countMessageObjAdjectives))
                .curUpdatedObjAdjectives(List.of(Map.of()))
                .curUpdatedSubAdjectives(List.of(Map.of()))
                .curUpdatedSubjects(List.of(Map.of()))
                .curUpdatedObjects(List.of(Map.of()))
                .curUpdatedStyles(List.of(Map.of()))
                .curUpdatedVerbs(List.of(Map.of()))
                .build();
        messagingTemplate.convertAndSend("/ws/sub/dottegi", Map.of("remainingTime", "이미지 생성중... 100%"));

        clearAllCountMessages();
        lastUpdatedPayload = payload;
        messagingTemplate.convertAndSend("/ws/sub/dottegi", payload);
    }

    private void clearAllCountMessages() {
        countMessageStyles.clear();
        countMessageSubjects.clear();
        countMessageObjects.clear();
        countMessageVerbs.clear();
        countMessageSubAdjectives.clear();
        countMessageObjAdjectives.clear();
    }

}

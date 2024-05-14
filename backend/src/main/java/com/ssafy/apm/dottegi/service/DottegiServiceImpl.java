package com.ssafy.apm.dottegi.service;

import com.ssafy.apm.common.dto.ImageResponseDto;
import com.ssafy.apm.common.service.ImageService;
import com.ssafy.apm.common.util.GoogleTranslator;
import com.ssafy.apm.sdw.dto.SdwRequestDto;
import com.ssafy.apm.sdw.dto.SdwResponseDto;
import com.ssafy.apm.sdw.service.SdwService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DottegiServiceImpl implements DottegiService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ImageService imageService;
    private final SdwService sdwService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* TODO: RDB에 저장해놨다가, 접속하면 Latest Payload를 돌려주는 것으로 대체필요 */
    @Getter
    private volatile Map<String, Object> latestPayload;
    private final ConcurrentHashMap<String, Integer> messageCountStyle = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountSubject = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountObject = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountVerb = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountSubAdjective = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountObjAdjective = new ConcurrentHashMap<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void processMessageStyle(String message) {
        messageCountStyle.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountStyle, "/dottegi/style");
    }

    @Override
    public void processMessageSubject(String message) {
        messageCountSubject.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountSubject, "/dottegi/subject");
    }

    @Override
    public void processMessageObject(String message) {
        messageCountObject.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountObject, "/dottegi/object");
    }

    @Override
    public void processMessageVerb(String message) {
        messageCountVerb.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountVerb, "/dottegi/verb");
    }

    @Override
    public void processMessageSubAdjective(String message) {
        messageCountSubAdjective.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountSubAdjective, "/dottegi/sub-adjective");
    }

    @Override
    public void processMessageObjAdjective(String message) {
        messageCountObjAdjective.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountObjAdjective, "/dottegi/obj-adjective");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendTop10Updates(ConcurrentHashMap<String, Integer> map, String destination) {
        List<Map.Entry<String, Integer>> top10 = getTop10Entries(map);
        messagingTemplate.convertAndSend(destination, top10);
    }

    private List<Map.Entry<String, Integer>> getTop10Entries(ConcurrentHashMap<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private long timerStart = System.currentTimeMillis();
//    private final long timerDuration = 10000;
    private final long timerDuration = 300000;

    @Scheduled(fixedRate = 1000)
    public void broadcastRemainingTime() throws IOException {
        long now = System.currentTimeMillis();
        long elapsed = now - timerStart;
        long remainingMillis = timerDuration - elapsed;

        if (remainingMillis <= 0) {
            sendTopMessageImage();
            timerStart = now;
            remainingMillis = timerDuration;
        }

        long remainingSeconds = remainingMillis / 1000;
        String remainingTime = String.format("%02d:%02d",
                remainingSeconds / 60,
                remainingSeconds % 60);
        Map<String, Object> payload = new HashMap<>();
        payload.put("remainingTime", remainingTime);
        messagingTemplate.convertAndSend("/dottegi", payload);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void sendTopMessageImage() throws IOException, IOException {
        /* DONE: 각각의 품사의 Top 10 문자열들을 불러온다.
            Top 1 단어들만 뽑아 합쳐 하나의 문자열을 만든다.
            Top 1 부터 10까지 각각의 품사들을 리스트에 담는다.
            Map을 초기화하고, /dottegi를 구독중인 유저들에게 합쳐진 하나의 문장과 리스트를 전송한다. */

        // Extract the top word from each category
        String topSubject = getTopWord(messageCountSubject);
        String topObject = getTopWord(messageCountObject);
        String topVerb = getTopWord(messageCountVerb);
        String topSubAdjective = getTopWord(messageCountSubAdjective);
        String topObjAdjective = getTopWord(messageCountObjAdjective);

        // Combine the top words into a single sentence
        String combinedSentence = String.format("%s %s이(가) %s %s을(를) %s하(고) 있다.",
                topSubAdjective, topSubject, topObjAdjective, topObject, topVerb);
        String prompt = GoogleTranslator.translate("ko", "en", combinedSentence);

        Random random = new Random();
        String[] styles = {"Anime", "Cartoon", "Realistic"};
        String style = messageCountStyle.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseGet(() -> styles[random.nextInt(styles.length)]);

        /* DONE: 영문 번역하여 Stable-Diffusion 에 요청하여 Base64 Encoded Image 받아옴 */
        SdwRequestDto sdwRequestDto = new SdwRequestDto();
        switch (style) {
            case "Anime" -> sdwRequestDto.updateAnimePrompt(prompt);
            case "Cartoon" -> sdwRequestDto.updateDisneyPrompt(prompt);
            case "Realistic" -> sdwRequestDto.updateRealisticPrompt(prompt);
        }
        SdwResponseDto sdwResponseDto = sdwService.requestStableDiffusion(sdwRequestDto);
        String base64Image = sdwResponseDto.getImages().get(0);

        /* DONE: RDB에 저장 or 파일로 저장하여 불러와서 Base64 인코딩 하여 전송하는 방식 */
        ImageResponseDto imageResponseDto = imageService.saveBase64Image(combinedSentence, base64Image);

        // Collect top 10 words from each category into lists
        List<Map.Entry<String, Integer>> top10Subjects = getTop10Entries(messageCountSubject);
        List<Map.Entry<String, Integer>> top10Objects = getTop10Entries(messageCountObject);
        List<Map.Entry<String, Integer>> top10Verbs = getTop10Entries(messageCountVerb);
        List<Map.Entry<String, Integer>> top10SubAdjectives = getTop10Entries(messageCountSubAdjective);
        List<Map.Entry<String, Integer>> top10ObjAdjectives = getTop10Entries(messageCountObjAdjective);

        // Clear the maps
        messageCountStyle.clear();
        messageCountSubject.clear();
        messageCountObject.clear();
        messageCountVerb.clear();
        messageCountSubAdjective.clear();
        messageCountObjAdjective.clear();

        // Send the combined sentence and lists to subscribers
        Map<String, Object> payload = new HashMap<>();
        payload.put("style", style);
        payload.put("image", imageResponseDto.getUrl());
        payload.put("sentence", combinedSentence);
        payload.put("topSubjects", top10Subjects);
        payload.put("topObjects", top10Objects);
        payload.put("topVerbs", top10Verbs);
        payload.put("topSubAdjectives", top10SubAdjectives);
        payload.put("topObjAdjectives", top10ObjAdjectives);

        latestPayload = payload;
        messagingTemplate.convertAndSend("/dottegi", payload);
    }

    private String getTopWord(ConcurrentHashMap<String, Integer> map) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None"); // Return "None" if no words have been recorded
    }
}

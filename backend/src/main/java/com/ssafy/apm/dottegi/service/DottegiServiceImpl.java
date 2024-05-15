package com.ssafy.apm.dottegi.service;

import com.ssafy.apm.common.dto.ImageResponseDto;
import com.ssafy.apm.common.service.ImageService;
import com.ssafy.apm.common.util.GoogleTranslator;
import com.ssafy.apm.sdw.dto.SdwRequestDto;
import com.ssafy.apm.sdw.dto.SdwResponseDto;
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
    /* TODO: RDB에 저장해놨다가, 접속하면 Latest Payload를 돌려주는 것으로 대체필요 */
    @Getter
    private volatile Map<String, Object> latestPayload;
    private final ConcurrentHashMap<String, Integer> messageCountStyle = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountSubject = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountObject = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountVerb = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountSubAdjective = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> messageCountObjAdjective = new ConcurrentHashMap<>();

    private String lastTopStyle = "";
    private String lastTopSubject = "";
    private String lastTopObject = "";
    private String lastTopVerb = "";
    private String lastTopSubAdjective = "";
    private String lastTopObjAdjective = "";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void processMessageStyle(String message) {
        messageCountStyle.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountStyle, "/ws/sub/dottegi/style");
    }

    @Override
    public void processMessageSubject(String message) {
        messageCountSubject.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountSubject, "/ws/sub/dottegi/subject");
    }

    @Override
    public void processMessageObject(String message) {
        messageCountObject.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountObject, "/ws/sub/dottegi/object");
    }

    @Override
    public void processMessageVerb(String message) {
        messageCountVerb.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountVerb, "/ws/sub/dottegi/verb");
    }

    @Override
    public void processMessageSubAdjective(String message) {
        messageCountSubAdjective.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountSubAdjective, "/ws/sub/dottegi/sub-adjective");
    }

    @Override
    public void processMessageObjAdjective(String message) {
        messageCountObjAdjective.merge(message, 1, Integer::sum);
        sendTop10Updates(messageCountObjAdjective, "/ws/sub/dottegi/obj-adjective");
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
    private final long timerDuration = 30000;
//    private final long timerDuration = 300000;

    @Scheduled(fixedRate = 1000)
    public void broadcastRemainingTime() throws IOException {
        long now = System.currentTimeMillis();
        long elapsed = now - timerStart;
        long remainingMillis = timerDuration - elapsed;

        if (remainingMillis <= 0) {
            timerStart = now;
            remainingMillis = timerDuration;
            sendTopMessageImage();
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
    private void sendTopMessageImage() throws IOException {
        /* DONE: 각각의 품사의 Top 10 문자열들을 불러온다.
            Top 1 단어들만 뽑아 합쳐 하나의 문자열을 만든다.
            Top 1 부터 10까지 각각의 품사들을 리스트에 담는다.
            Map을 초기화하고, /dottegi를 구독중인 유저들에게 합쳐진 하나의 문장과 리스트를 전송한다. */

        // Extract the top style or get random style
        String topStyle = messageCountStyle.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseGet(() -> lastTopStyle);

        // Extract the top word from each category
        String topSubject = getTopWord(messageCountSubject);
        String topObject = getTopWord(messageCountObject);
        String topVerb = getTopWord(messageCountVerb);
        String topSubAdjective = getTopWord(messageCountSubAdjective);
        String topObjAdjective = getTopWord(messageCountObjAdjective);

        /* TODO: 최초 실행시 Null, 공백 등 초기화된 상태이면 랜덤한 이미지 or Prompt를 가져오도록 설계 필요 */

        // If the top word is None, use the last top word
        Random random = new Random();
        String[] styles = {"Anime", "Cartoon", "Realistic"};
        if (topStyle.isEmpty()) topStyle = styles[random.nextInt(styles.length)];
        if (topSubject.equals("None")) topSubject = lastTopSubject;
        if (topObject.equals("None")) topObject = lastTopObject;
        if (topVerb.equals("None")) topVerb = lastTopVerb;
        if (topSubAdjective.equals("None")) topSubAdjective = lastTopSubAdjective;
        if (topObjAdjective.equals("None")) topObjAdjective = lastTopObjAdjective;

        if (topStyle.equals(lastTopStyle) && topVerb.equals(lastTopVerb) &&
                topSubject.equals(lastTopSubject) && topObject.equals(lastTopObject) &&
                topSubAdjective.equals(lastTopSubAdjective) && topObjAdjective.equals(lastTopObjAdjective)) {
            log.info("SAME DATA INCOME!!!");
            // Clear the maps
            messageCountStyle.clear();
            messageCountSubject.clear();
            messageCountObject.clear();
            messageCountVerb.clear();
            messageCountSubAdjective.clear();
            messageCountObjAdjective.clear();
            messagingTemplate.convertAndSend("/ws/sub/dottegi", latestPayload);
            return;
        }

        lastTopStyle = topStyle;
        lastTopSubject = topSubject;
        lastTopObject = topObject;
        lastTopVerb = topVerb;
        lastTopSubAdjective = topSubAdjective;
        lastTopObjAdjective = topObjAdjective;

        // Combine the top words into a single sentence
        String combinedSentence = String.format("%s %s이(가) %s %s을(를) %s하(고) 있다.",
                topSubAdjective, topSubject, topObjAdjective, topObject, topVerb);
        String prompt = GoogleTranslator.translate("ko", "en", combinedSentence);

        /* DONE: 영문 번역하여 Stable-Diffusion 에 요청하여 Base64 Encoded Image 받아옴 */
        SdwRequestDto sdwRequestDto = new SdwRequestDto();
        switch (topStyle) {
            case "Anime" -> sdwRequestDto.updateAnimePrompt(prompt);
            case "Cartoon" -> sdwRequestDto.updateDisneyPrompt(prompt);
            case "Realistic" -> sdwRequestDto.updateRealisticPrompt(prompt);
        }
        //SdwResponseDto sdwResponseDto = sdwService.requestStableDiffusion(sdwRequestDto);
        //String base64Image = sdwResponseDto.getImages().get(0);

        /* DONE: RDB에 저장 or 파일로 저장하여 불러와서 Base64 인코딩 하여 전송하는 방식 */
        //ImageResponseDto imageResponseDto = imageService.saveBase64Image(combinedSentence, base64Image);
        ImageResponseDto imageResponseDto = imageService.findImageByUuid("2c2e4dce-34c1-4f27-838a-6e1bc1bc00e9");

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
        payload.put("style", topStyle);
        payload.put("image", imageResponseDto.getUrl().replace(".png", ""));
        payload.put("sentence", combinedSentence);
        payload.put("topSubjects", top10Subjects);
        payload.put("topObjects", top10Objects);
        payload.put("topVerbs", top10Verbs);
        payload.put("topSubAdjectives", top10SubAdjectives);
        payload.put("topObjAdjectives", top10ObjAdjectives);

        latestPayload = payload;
        messagingTemplate.convertAndSend("/ws/sub/dottegi", payload);
    }

    private String getTopWord(ConcurrentHashMap<String, Integer> map) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None"); // Return "None" if no words have been recorded
    }

    @Override
    public void sendLastUpdatedPayload(String sessionId) {
        messagingTemplate.convertAndSendToUser(sessionId,"/ws/sub/dottegi", latestPayload);
        messagingTemplate.convertAndSendToUser(sessionId,"/ws/sub/dottegi/style", getTop10Entries(messageCountStyle));
        messagingTemplate.convertAndSendToUser(sessionId,"/ws/sub/dottegi/subject", getTop10Entries(messageCountSubject));
        messagingTemplate.convertAndSendToUser(sessionId,"/ws/sub/dottegi/object", getTop10Entries(messageCountObject));
        messagingTemplate.convertAndSendToUser(sessionId,"/ws/sub/dottegi/verb", getTop10Entries(messageCountVerb));
        messagingTemplate.convertAndSendToUser(sessionId,"/ws/sub/dottegi/sub-adjective", getTop10Entries(messageCountSubAdjective));
        messagingTemplate.convertAndSendToUser(sessionId,"/ws/sub/dottegi/obj-adjective", getTop10Entries(messageCountObjAdjective));
    }

}

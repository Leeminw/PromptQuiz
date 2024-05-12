package com.ssafy.apm.dottegi.controller;

import com.ssafy.apm.dottegi.service.DottegiService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DottegiController {

    private final DottegiService dottegiService;

    /* TODO: GetMapping 으로 RDB에서 불러오는 방식 구현 필요 */
//    @MessageMapping("/register")
//    @SendToUser("/dottegi")
//    public Map<String, Object> registerNewUser(SimpMessageHeaderAccessor headerAccessor) {
//        // This method gets called when a new user subscribes to the "/dottegi" topic
//        // Check if there's stored data
//        Map<String, Object> lastPayload = dottegiService.getLastPayload();
//        if (lastPayload != null) {
//            return lastPayload;
//        } else {
//            return new HashMap<>(); // Or send some default welcome message
//        }
//    }

    /* TODO: DTO 클래스로 return */
    /** FullPath: /chat/dottegi/subject */
    @MessageMapping("/dottegi/subject")
    @SendTo("/dottegi/subject")
    public String handleMessage4Subject(String message) {
        dottegiService.processMessageSubject(message);
        return message;
    }

    /** FullPath: /chat/dottegi/object */
    @MessageMapping("/dottegi/object")
    @SendTo("/dottegi/object")
    public String handleMessage4Object(String message) {
        dottegiService.processMessageObject(message);
        return message;
    }

    /** FullPath: /chat/dottegi/verb */
    @MessageMapping("/dottegi/verb")
    @SendTo("/dottegi/verb")
    public String handleMessage4Verb(String message) {
        dottegiService.processMessageVerb(message);
        return message;
    }

    /** FullPath: /chat/dottegi/sub-adjective */
    @MessageMapping("/dottegi/sub-adjective")
    @SendTo("/dottegi/sub-adjective")
    public String handleMessage4SubAdjective(String message) {
        dottegiService.processMessageSubAdjective(message);
        return message;
    }

    /** FullPath: /chat/dottegi/obj-adjective */
    @MessageMapping("/dottegi/obj-adjective")
    @SendTo("/dottegi/obj-adjective")
    public String handleMessage4ObjAdjective(String message) {
        dottegiService.processMessageObjAdjective(message);
        return message;
    }

}

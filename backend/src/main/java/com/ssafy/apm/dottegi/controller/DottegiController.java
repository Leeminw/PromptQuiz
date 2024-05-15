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

    @MessageMapping("/dottegi/style")
    @SendTo("/ws/sub/dottegi/style")
    public void handleMessage4Style(String message) {
        dottegiService.processMessageStyle(message);
    }

    /* TODO: DTO 클래스로 return */
    /** FullPath: /ws/pub/dottegi/subject */
    @MessageMapping("/dottegi/subject")
    @SendTo("/ws/sub/dottegi/subject")
    public String handleMessage4Subject(String message) {
        dottegiService.processMessageSubject(message);
        return message;
    }

    /** FullPath: /ws/pub/dottegi/object */
    @MessageMapping("/dottegi/object")
    @SendTo("/ws/sub/dottegi/object")
    public String handleMessage4Object(String message) {
        dottegiService.processMessageObject(message);
        return message;
    }

    /** FullPath: /ws/pub/dottegi/verb */
    @MessageMapping("/dottegi/verb")
    @SendTo("/ws/sub/dottegi/verb")
    public String handleMessage4Verb(String message) {
        dottegiService.processMessageVerb(message);
        return message;
    }

    /** FullPath: /ws/pub/dottegi/sub-adjective */
    @MessageMapping("/dottegi/sub-adjective")
    @SendTo("/ws/sub/dottegi/sub-adjective")
    public String handleMessage4SubAdjective(String message) {
        dottegiService.processMessageSubAdjective(message);
        return message;
    }

    /** FullPath: /ws/pub/dottegi/obj-adjective */
    @MessageMapping("/dottegi/obj-adjective")
    @SendTo("/ws/sub/dottegi/obj-adjective")
    public String handleMessage4ObjAdjective(String message) {
        dottegiService.processMessageObjAdjective(message);
        return message;
    }

}

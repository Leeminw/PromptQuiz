package com.ssafy.apm.dottegi.service;

public interface DottegiService {

    void processMessageSubject(String message);
    void processMessageObject(String message);
    void processMessageVerb(String message);
    void processMessageSubAdjective(String message);
    void processMessageObjAdjective(String message);

}

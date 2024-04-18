package com.ssafy.apm.common.controller;

import com.ssafy.apm.common.domain.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ResponseData<?>> home() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success());
    }

    @GetMapping("/index")
    public ResponseEntity<ResponseData<?>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success());
    }

}

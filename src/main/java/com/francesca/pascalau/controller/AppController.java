package com.francesca.pascalau.controller;

import com.francesca.pascalau.domain.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class AppController {

    private final PublisherService publisherService;

    @PostMapping("/publish")
    public ResponseEntity<String> postMessage(@RequestBody String message) {
        publisherService.sendMessage(message);

        return new ResponseEntity<>("Message sent", new HttpHeaders(), HttpStatus.OK);
    }
}

package com.francesca.pascalau.controller;

import com.francesca.pascalau.domain.sqs.MessagePublisherService;
import com.francesca.pascalau.domain.sqs.PublisherService;
import com.francesca.pascalau.domain.sqs.RetryConsumerService;
import com.francesca.pascalau.domain.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class AppController {

    private final S3Service s3Service;
    private final PublisherService publisherService;
    private final RetryConsumerService retryConsumerService;
    private final MessagePublisherService messagePublisherService;

    @GetMapping("/read")
    public ResponseEntity<String> readMessage() {
        retryConsumerService.startListeningToMessages();

        return new ResponseEntity<>("Message read", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> postMessage(@RequestBody String message) {
        publisherService.sendMessage(message);

        return new ResponseEntity<>("Message sent", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/publish/message")
    public ResponseEntity<String> post(@RequestBody String message) {
        messagePublisherService.send(message);

        return new ResponseEntity<>("Message sent", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public void upload(@RequestBody String message) {
        s3Service.uploadFile(LocalDate.now() + "_" + UUID.randomUUID(), message);
    }
}

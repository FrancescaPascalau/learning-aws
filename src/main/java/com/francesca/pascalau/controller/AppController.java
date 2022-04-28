package com.francesca.pascalau.controller;

import com.francesca.pascalau.domain.PublisherService;
import com.francesca.pascalau.domain.UploadService;
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

    private final PublisherService publisherService;
    private final UploadService uploadService;

    @PostMapping("/publish")
    public ResponseEntity<String> postMessage(@RequestBody String message) {
        publisherService.sendMessage(message);

        return new ResponseEntity<>("Message sent", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public void upload(@RequestBody String message) {
        uploadService.uploadFile(LocalDate.now() + ".txt", message);
    }
}

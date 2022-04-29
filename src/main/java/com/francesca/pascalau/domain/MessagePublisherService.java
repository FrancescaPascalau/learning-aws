package com.francesca.pascalau.domain;

import com.amazonaws.services.sqs.AmazonSQS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisherService {

    private final AmazonSQS amazonSQS;

    @Value("${sqs.queue}")
    private String queue;

    public void send(String message) {
        log.info("Sending message to SQS: {}", message);
        amazonSQS.sendMessage(queue, message);
    }
}
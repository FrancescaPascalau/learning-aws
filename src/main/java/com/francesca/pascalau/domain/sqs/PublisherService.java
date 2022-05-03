package com.francesca.pascalau.domain.sqs;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${sqs.queue}")
    private String queue;

    public void sendMessage(String message) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("AttributeOne", new MessageAttributeValue()
                .withStringValue("This is an attribute")
                .withDataType("String"));

        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
                .withQueueUrl(queue)
                .withMessageBody(message)
                .withDelaySeconds(30)
                .withMessageAttributes(messageAttributes);

        log.info("Sending message to SQS: {}", sendMessageStandardQueue.getMessageBody());
        queueMessagingTemplate.convertAndSend(queue, sendMessageStandardQueue);
    }
}
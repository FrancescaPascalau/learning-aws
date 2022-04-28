package com.francesca.pascalau.domain;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.MessageSystemAttributeName;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryConsumerService {

    private static final int MAX_RETRIES = 3;

    private final AmazonSQS amazonSQS;

    @Value("${sqs.queue}")
    private String queue;

    public void startListeningToMessages() {
        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queue)
                .withAttributeNames(MessageSystemAttributeName.ApproximateReceiveCount.toString())
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(3);

        final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
        for (Message messageObject : messages) {
            String messageCount = messageObject.getAttributes().get(MessageSystemAttributeName.ApproximateReceiveCount.name());

            if (Integer.parseInt(messageCount) >= MAX_RETRIES) {
                log.error("Max message retries exceeded");
            } else {
                log.info("Message approximate count is: " + messageCount);
                String message = messageObject.getBody();
                log.info("Received message: " + message);
            }
        }
    }
}

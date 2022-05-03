package com.francesca.pascalau.domain.sqs;

import com.francesca.pascalau.domain.sqs.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerService {

    @SqsListener(value = "${sqs.queue}", deletionPolicy = ON_SUCCESS)
    public void processMessage(Message message, @Header(name = "ApproximateReceiveCount") Integer count) {
        try {
            log.info("Received new SQS message: {}, with retry count: {}", message, count);
        } catch (Exception e) {
            throw new RuntimeException("Cannot process message from SQS", e);
        }
    }
}

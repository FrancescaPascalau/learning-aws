package com.francesca.pascalau.domain.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {

    private final AmazonS3 amazonS3;

    @Value("${s3.name}")
    private String bucketName;

    public void uploadFile(String keyName, String message) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            amazonS3.putObject(bucketName, keyName, convertMessage(message), metadata);
            log.info("File uploaded: " + keyName);
        } catch (AmazonServiceException serviceException) {
            log.info("AmazonServiceException: " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            log.info("AmazonClientException: " + clientException.getMessage());
            throw clientException;
        }
    }

    private ByteArrayInputStream convertMessage(String message) {
        return new ByteArrayInputStream(message.getBytes());
    }
}
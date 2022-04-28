package com.francesca.pascalau.domain;

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

    public void upload(String message) {
        log.info("Uploading message to S3 as file: {}", message);

//        generatePreSignedUrl(message);
    }

//    private void generatePreSignedUrl(String filePath) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.MINUTE, 10); //validity of 10 minutes
//
//        amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), HttpMethod.PUT);
//    }

    public String uploadFile(String keyName, String message) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            amazonS3.putObject(bucketName, keyName, convertMessage(message), metadata);
            return "File uploaded: " + keyName;
        } catch (AmazonServiceException serviceException) {
            log.info("AmazonServiceException: " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            log.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
    }

    private ByteArrayInputStream convertMessage(String message) {
        return new ByteArrayInputStream(message.getBytes());
    }
}
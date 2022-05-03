package com.francesca.pascalau.domain.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${s3.name}")
    private String bucketName;

    public void uploadFile(String keyName, String message) {
        create();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            amazonS3.putObject(bucketName, keyName, convertMessage(message), metadata);
            log.info("File uploaded: {}", keyName);
        } catch (Exception e) {
            log.warn("Exception while uploading file: {}", e.getMessage());
        }

        listAllFiles();
    }

    private ByteArrayInputStream convertMessage(String message) {
        return new ByteArrayInputStream(message.getBytes());
    }

    private void create() {
        log.info("Creating bucket with name: {}", bucketName);

        amazonS3.createBucket(bucketName);
    }

    public void listAllFiles() {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            log.info("Retrieving files from bucket: {}", objectSummary.getKey());
        }
    }
}
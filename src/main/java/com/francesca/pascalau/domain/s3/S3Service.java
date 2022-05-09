package com.francesca.pascalau.domain.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
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

//    private static final int CONTENT_LENGTH = 50;

    private final AmazonS3 amazonS3;

    @Value("${s3.name}")
    private String bucketName;

    public void uploadFile(String keyName, String message) {
        create();

        try {
            ByteArrayInputStream inputStream = convertMessage(message);

            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(CONTENT_LENGTH);

            amazonS3.putObject(bucketName, keyName, inputStream, metadata);
            log.info("File uploaded: {}", keyName);

            downloadAllFiles(bucketName, keyName);
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

        if (amazonS3.doesBucketExistV2(bucketName)) {
            log.warn("Bucket already exists.");
            return;
        }

        amazonS3.createBucket(bucketName);
    }

    private void listAllFiles() {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        log.info("Retrieving files from bucket: \n");
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            log.info("{}", objectSummary.getKey());
        }
    }

    private void downloadAllFiles(String bucketName, String keyName) {
        log.info("Downloading files from bucket: \n");
        S3Object s3Object = amazonS3.getObject(bucketName, keyName);
        log.info("Downloaded file: {}", s3Object);
    }
}
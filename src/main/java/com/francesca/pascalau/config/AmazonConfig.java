package com.francesca.pascalau.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class AmazonConfig {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.accessKey}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String awsSecretKey;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }

    @Bean
    protected MessageConverter messageConverter(ObjectMapper objectMapper) {
        var converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

//    @Bean
//    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(
//            AmazonSQSAsync amazonSqsAsync) {
//        final var listenerContainerFactory = new SimpleMessageListenerContainerFactory();
//        listenerContainerFactory.setAutoStartup(true);
//        listenerContainerFactory.setAmazonSqs(amazonSqsAsync);
//        //listenerContainerFactory.setMaxNumberOfMessages(20);
//        listenerContainerFactory.setVisibilityTimeout(120);
//        //listenerContainerFactory.setTaskExecutor(createDefaultTaskExecutor());
//        return listenerContainerFactory;
//    }
//
//    private AsyncTaskExecutor createDefaultTaskExecutor() {
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(40);
//        threadPoolTaskExecutor.setMaxPoolSize(40);
//        threadPoolTaskExecutor.setQueueCapacity(40);
//        threadPoolTaskExecutor.afterPropertiesSet();
//        threadPoolTaskExecutor.initialize();
//        return threadPoolTaskExecutor;
//    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }
}
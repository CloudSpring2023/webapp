package com.example.assignment1.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
//    @Value("${aws.accessKey}")
//    private String accessKey;

//    @Value("${aws.secretKey}")
//    private String accessSecret;

    @Value("${aws.region}")
    private String region;

    @Bean
    public AmazonS3 s3() {
//        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, accessSecret);
//        return AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        return AmazonS3ClientBuilder.standard().build();
    }
}

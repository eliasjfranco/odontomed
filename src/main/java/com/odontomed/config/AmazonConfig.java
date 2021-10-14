package com.odontomed.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Value("${aws.access.key}")
    private String accesKey;

    @Value("${aws.access.secret.key}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AmazonS3 s3(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(accesKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.SA_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }



}

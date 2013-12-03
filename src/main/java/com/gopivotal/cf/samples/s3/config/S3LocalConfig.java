package com.gopivotal.cf.samples.s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
@EnableConfigurationProperties(S3Properties.class)
public class S3LocalConfig {

    Log log = LogFactory.getLog(S3LocalConfig.class);

    @Autowired
    private S3Properties s3Properties;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3Properties.getAwsAccessKey(), s3Properties.getAwsSecretKey());
        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.createBucket(s3Properties.getBucket());
        log.info("Using S3 Bucket: " + s3Properties.getBucket());
        return amazonS3;
    }
}

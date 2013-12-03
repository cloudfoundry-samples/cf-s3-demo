package com.gopivotal.cf.samples.s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.gopivotal.cf.samples.s3.repository.S3;
import com.mongodb.Mongo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@Configuration
@Profile("default")
@EnableConfigurationProperties({MongoProperties.class, S3Properties.class})
public class LocalConfig {

    Log log = LogFactory.getLog(LocalConfig.class);

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private S3Properties s3Properties;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        try {
            UserCredentials userCredentials = new UserCredentials(mongoProperties.getUsername(), mongoProperties.getPassword());
            return new SimpleMongoDbFactory(new Mongo(mongoProperties.getHost(), mongoProperties.getPort()), mongoProperties.getDbname(), userCredentials);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Error creating MongoDbFactory: ", e);
        }
    }

    @Bean
    public S3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3Properties.getAwsAccessKey(), s3Properties.getAwsSecretKey());
        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.createBucket(s3Properties.getBucket());
        log.info("Using S3 Bucket: " + s3Properties.getBucket());
        return new S3(amazonS3, s3Properties.getBucket());
    }

}

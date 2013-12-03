package com.gopivotal.cf.samples.s3;

import com.gopivotal.cf.samples.s3.repository.S3;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;

@Configuration
@Profile("cloud")
public class CloudConfig {

    @Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
        return cloud().getSingletonServiceConnector(MongoDbFactory.class, null);
    }

    @Bean
    public S3 s3() {
        return cloud().getSingletonServiceConnector(S3.class, null);
    }
}

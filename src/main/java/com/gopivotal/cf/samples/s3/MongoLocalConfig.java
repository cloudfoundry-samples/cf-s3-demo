package com.gopivotal.cf.samples.s3;

import com.mongodb.Mongo;
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
@Profile("mongodb-local")
@EnableConfigurationProperties(MongoProperties.class)
public class MongoLocalConfig {

    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        try {
            UserCredentials userCredentials = new UserCredentials(mongoProperties.getUsername(), mongoProperties.getPassword());
            return new SimpleMongoDbFactory(new Mongo(mongoProperties.getHost(), mongoProperties.getPort()), mongoProperties.getDbname(), userCredentials);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Error creating MongoDbFactory: ", e);
        }
    }

}

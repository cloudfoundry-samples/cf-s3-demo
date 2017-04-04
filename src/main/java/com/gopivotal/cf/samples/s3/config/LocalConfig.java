package com.gopivotal.cf.samples.s3.config;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.gopivotal.cf.samples.s3.repository.S3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("default")
@EnableConfigurationProperties({MySqlProperties.class, S3Properties.class})
public class LocalConfig {

    Log log = LogFactory.getLog(LocalConfig.class);
    @Autowired
    private MySqlProperties mySqlProperties;
    @Autowired
    private S3Properties s3Properties;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mySqlProperties.getDriver());
        dataSource.setUrl(mySqlProperties.getUrl());
        dataSource.setUsername(mySqlProperties.getUsername());
        dataSource.setPassword(mySqlProperties.getPassword());
        return dataSource;
    }

    @Bean
    public S3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withPathStyleAccessEnabled(s3Properties.getPathStyleAccess());

        if (s3Properties.getEndpoint() != null) {
            // if a custom endpoint is set, we will ignore the region
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                    s3Properties.getEndpoint(), "Standard"
            ));
        } else {
            builder.withRegion(s3Properties.getRegion());
        }

        AmazonS3 amazonS3 = builder.build();

        try {
            amazonS3.createBucket(
                    new CreateBucketRequest(s3Properties.getBucket()).withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (AmazonServiceException e) {
            if (!e.getErrorCode().equals("BucketAlreadyOwnedByYou")) {
                throw e;
            }
        }
        log.info("Using S3 Bucket: " + s3Properties.getBucket());
        return new S3(amazonS3, s3Properties.getBucket(), s3Properties.getBaseUrl());
    }

}

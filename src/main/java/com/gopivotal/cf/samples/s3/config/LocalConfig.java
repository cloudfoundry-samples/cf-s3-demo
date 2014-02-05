package com.gopivotal.cf.samples.s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.gopivotal.cf.samples.s3.repository.S3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

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
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3Properties.getAwsAccessKey(), s3Properties.getAwsSecretKey());
        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.createBucket(s3Properties.getBucket());
        log.info("Using S3 Bucket: " + s3Properties.getBucket());
        return new S3(amazonS3, s3Properties.getBucket());
    }

}

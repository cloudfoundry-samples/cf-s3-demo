package com.gopivotal.cf.samples.s3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(name = "s3", path = "classpath:application.yml")
public class S3Properties {

    private String awsAccessKey;
    private String awsSecretKey;
    private String bucket;

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

}
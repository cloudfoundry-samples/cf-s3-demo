package com.gopivotal.cf.samples.s3.connector.cloudfoundry;

import org.springframework.cloud.service.BaseServiceInfo;

public class S3ServiceInfo extends BaseServiceInfo {

    private String awsAccessKey;
    private String awsSecretKey;
    private String bucket;

    public S3ServiceInfo(String id, String awsAccessKey, String awsSecretKey, String bucket) {
        super(id);
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.bucket = bucket;
    }

    @ServiceProperty
    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    @ServiceProperty
    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    @ServiceProperty
    public String getBucket() {
        return bucket;
    }
}

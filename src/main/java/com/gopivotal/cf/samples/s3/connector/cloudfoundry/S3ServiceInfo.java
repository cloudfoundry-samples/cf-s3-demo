package com.gopivotal.cf.samples.s3.connector.cloudfoundry;

import org.springframework.cloud.service.BaseServiceInfo;

public class S3ServiceInfo extends BaseServiceInfo {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;
    private String endpoint;
    private Boolean pathStyleAccess;
    private String baseUrl;

    public S3ServiceInfo(String id, String accessKey, String secretKey, String bucket, String region,
                         String endpoint, Boolean pathStyleAccess, String baseUrl) {
        super(id);
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
        this.region = region;
        this.endpoint = endpoint;
        this.pathStyleAccess = pathStyleAccess;
        this.baseUrl = baseUrl;
    }

    @ServiceProperty
    public String getAccessKey() {
        return accessKey;
    }

    @ServiceProperty
    public String getSecretKey() {
        return secretKey;
    }

    @ServiceProperty
    public String getBucket() {
        return bucket;
    }

    @ServiceProperty
    public String getRegion() {
        return region;
    }

    @ServiceProperty
    public String getEndpoint() {
        return endpoint;
    }

    @ServiceProperty
    public Boolean getPathStyleAccess() {
        return pathStyleAccess;
    }

    @ServiceProperty
    public String getBaseUrl() {
        return baseUrl;
    }
}

package com.gopivotal.cf.samples.s3.connector.cloudfoundry;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

public class S3ServiceInfoCreator extends CloudFoundryServiceInfoCreator<S3ServiceInfo> {

    public S3ServiceInfoCreator() {
        super(new Tags("s3"));
    }

    @Override
    public S3ServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked") Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

        String id = (String) serviceData.get("name");
        String accessKey = (String) credentials.get("accessKey");
        String secretKey = (String) credentials.get("secretKey");
        String bucket = (String) credentials.get("bucket");
        String region = (String) credentials.getOrDefault("region", "us-west-1");
        String endpoint = (String) credentials.get("endpoint");
        Boolean pathStyleAccess = Boolean.valueOf((String) credentials.getOrDefault("pathStyleAccess", "false"));
        String baseUrl = (String) credentials.get("baseUrl");

        return new S3ServiceInfo(id, accessKey, secretKey, bucket, region, endpoint, pathStyleAccess, baseUrl);
    }

    @Override
    public boolean accept(Map<String, Object> serviceData) {
        String name = (String) serviceData.get("name");
        return name.startsWith("s3");
    }
}

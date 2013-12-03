package com.gopivotal.cf.samples.s3.connector.cloudfoundry;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pivotal
 * Date: 12/2/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class S3ServiceInfoCreator extends CloudFoundryServiceInfoCreator<S3ServiceInfo> {

    public S3ServiceInfoCreator() {
        super("s3");
    }

    @Override
    public S3ServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked") Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

        String id = (String) serviceData.get("name");
        String awsAccessKey = (String) credentials.get("awsAccessKey");
        String awsSecretKey = (String) credentials.get("awsSecretKey");
        String bucket = (String) credentials.get("bucket");

        return new S3ServiceInfo(id, awsAccessKey, awsSecretKey, bucket);
    }

    @Override
    public boolean accept(Map<String, Object> serviceData) {
        String name = (String) serviceData.get("name");
        return name.startsWith("s3");
    }
}

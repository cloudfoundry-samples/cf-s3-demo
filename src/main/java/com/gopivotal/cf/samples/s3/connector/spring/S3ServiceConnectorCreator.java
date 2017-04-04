package com.gopivotal.cf.samples.s3.connector.spring;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.gopivotal.cf.samples.s3.connector.cloudfoundry.S3ServiceInfo;
import com.gopivotal.cf.samples.s3.repository.S3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;

public class S3ServiceConnectorCreator extends AbstractServiceConnectorCreator<S3, S3ServiceInfo> {

    Log log = LogFactory.getLog(S3ServiceConnectorCreator.class);

    @Override
    public S3 create(S3ServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(serviceInfo.getAccessKey(), serviceInfo.getSecretKey());

        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withPathStyleAccessEnabled(serviceInfo.getPathStyleAccess());

        if (serviceInfo.getEndpoint() != null) {
            // if a custom endpoint is set, we will ignore the region
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                    serviceInfo.getEndpoint(), "Standard"
            ));
        } else {
            builder.withRegion(serviceInfo.getRegion());
        }

        AmazonS3 amazonS3 = builder.build();

        try {
            amazonS3.createBucket(
                    new CreateBucketRequest(serviceInfo.getBucket()).withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (AmazonServiceException e) {
            if (!e.getErrorCode().equals("BucketAlreadyOwnedByYou")) {
                throw e;
            }
        }
        log.info("Using S3 Bucket: " + serviceInfo.getBucket());
        return new S3(amazonS3, serviceInfo.getBucket(), serviceInfo.getBaseUrl());
    }

}

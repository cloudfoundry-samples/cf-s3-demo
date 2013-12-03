package com.gopivotal.cf.samples.s3.connector.spring;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.gopivotal.cf.samples.s3.repository.S3;
import com.gopivotal.cf.samples.s3.connector.cloudfoundry.S3ServiceInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;

public class S3ServiceConnectorCreator extends AbstractServiceConnectorCreator<S3, S3ServiceInfo> {

    Log log = LogFactory.getLog(S3ServiceConnectorCreator.class);

    @Override
    public S3 create(S3ServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(serviceInfo.getAwsAccessKey(), serviceInfo.getAwsSecretKey());
        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.createBucket(serviceInfo.getBucket());
        log.info("Using S3 Bucket: " + serviceInfo.getBucket());
        return new S3(amazonS3, serviceInfo.getBucket());
    }

}

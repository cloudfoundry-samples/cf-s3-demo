package com.gopivotal.cf.samples.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class S3 {

    Log log = LogFactory.getLog(S3.class);

    @Autowired
    private S3Properties s3Properties;

    private AmazonS3 amazonS3;

    @PostConstruct
    public void init() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3Properties.getAwsAccessKey(), s3Properties.getAwsSecretKey());
        amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.createBucket(s3Properties.getBucket());
        log.info("Using S3 Bucket: " + s3Properties.getBucket());
    }

    public void put(S3File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(s3Properties.getBucket(), file.getActualFileName(), file.getFile());
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
    }

    public void delete(S3File file) {
        amazonS3.deleteObject(s3Properties.getBucket(), file.getActualFileName());
    }

}
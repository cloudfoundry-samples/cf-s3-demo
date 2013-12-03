package com.gopivotal.cf.samples.s3.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class S3 {

    private AmazonS3 amazonS3;
    private String bucket;

    public S3(AmazonS3 amazonS3, String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    public S3File createS3FileObject(String id, String name, File file) {
        return new S3File(id, bucket, name, file);
    }

    public void put(S3File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, file.getActualFileName(), file.getFile());
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
    }

    public void delete(S3File file) {
        amazonS3.deleteObject(bucket, file.getActualFileName());
    }

}
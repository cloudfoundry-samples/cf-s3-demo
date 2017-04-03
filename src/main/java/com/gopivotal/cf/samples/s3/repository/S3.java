package com.gopivotal.cf.samples.s3.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class S3 {

    private AmazonS3 amazonS3;
    private String bucket;
    private String baseUrl;

    public S3(AmazonS3 amazonS3, String bucket, String baseUrl) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
        this.baseUrl = baseUrl;
    }

    public S3File createS3FileObject(String id, String name, File file) {
        return new S3File(id, bucket, name, file);
    }

    public URL put(S3File file) throws MalformedURLException {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, file.getActualFileName(), file.getFile())
            .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
        URL url;
        if (baseUrl == null) {
            url = amazonS3.getUrl(bucket, file.getActualFileName());
        }else{
            url = new URL(baseUrl + "/" + bucket + "/" + file.getActualFileName());
        }
        return url;
    }

    public void delete(S3File file) {
        amazonS3.deleteObject(bucket, file.getActualFileName());
    }

}
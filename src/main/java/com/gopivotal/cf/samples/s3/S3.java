package com.gopivotal.cf.samples.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S3 {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private S3Properties s3Properties;

    public void put(S3File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(s3Properties.getBucket(), file.getActualFileName(), file.getFile());
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
    }

    public void delete(S3File file) {
        amazonS3.deleteObject(s3Properties.getBucket(), file.getActualFileName());
    }

}
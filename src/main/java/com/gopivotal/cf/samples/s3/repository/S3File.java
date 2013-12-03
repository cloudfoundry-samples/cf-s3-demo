package com.gopivotal.cf.samples.s3.repository;

import org.springframework.data.annotation.Transient;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class S3File {

    private String id;
    private String bucket;
    private String name;
    private File file;

    public S3File() {
    }

    S3File(String id, String bucket, String name, File file) {
        this.id = id;
        this.bucket = bucket;
        this.name = name;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public String getBucket() {
        return bucket;
    }

    public String getName() {
        return name;
    }

    @Transient
    public File getFile() {
        return file;
    }

    @Transient
    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3.amazonaws.com/" + bucket + "/" + getActualFileName());
    }

    @Transient
    public String getActualFileName() {
        return id + "/" + name;
    }

}
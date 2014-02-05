package com.gopivotal.cf.samples.s3.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Entity
public class S3File {

    @Id
    private String id;
    private String bucket;
    private String name;
    @Transient
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

    public File getFile() {
        return file;
    }

    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3.amazonaws.com/" + bucket + "/" + getActualFileName());
    }

    public String getActualFileName() {
        return id + "/" + name;
    }

}
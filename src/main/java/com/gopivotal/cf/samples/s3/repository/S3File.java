package com.gopivotal.cf.samples.s3.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.File;
import java.net.URL;

@Entity
public class S3File {

    @Id
    private String id;
    private String bucket;
    private String name;
    private URL url;
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

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public String getActualFileName() {
        return id + "/" + name;
    }

}
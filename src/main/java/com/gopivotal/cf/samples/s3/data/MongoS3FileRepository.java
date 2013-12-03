package com.gopivotal.cf.samples.s3.data;

import com.gopivotal.cf.samples.s3.repository.S3File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoS3FileRepository extends MongoRepository<S3File, String> {
}

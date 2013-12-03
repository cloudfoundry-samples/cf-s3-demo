package com.gopivotal.cf.samples.s3;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoS3FileRepository extends MongoRepository<S3File, String> {
}

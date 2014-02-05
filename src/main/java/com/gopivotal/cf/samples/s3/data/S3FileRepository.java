package com.gopivotal.cf.samples.s3.data;

import com.gopivotal.cf.samples.s3.repository.S3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface S3FileRepository extends CrudRepository<S3File, String> {
}

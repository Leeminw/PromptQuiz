package com.ssafy.apm.common.repository;

import com.ssafy.apm.common.domain.S3File;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface S3FileRepository extends JpaRepository<S3File, Long> {
}

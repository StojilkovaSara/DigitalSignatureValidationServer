package com.example.signaturevalidationserver.repository;

import com.example.signaturevalidationserver.model.User;
import com.example.signaturevalidationserver.model.VerifiedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileRepository extends JpaRepository<VerifiedFiles, Long> {
    VerifiedFiles findByFileName(String file_name);

    @Query("SELECT f FROM VerifiedFiles f WHERE f.file_id = :file_id")
    VerifiedFiles findByFileId(@Param("file_id") Long file_id);

    @Query("SELECT f FROM VerifiedFiles f WHERE f.user = :user")
    List<VerifiedFiles> findAllByUser(@Param("user") User user);
    @Query("SELECT f.fileName AS fileName, f.filePath AS filePath, f.uploadDate AS uploadDate FROM VerifiedFiles f")
    List<VerifiedFiles> findAllVerifiedFiles();
}

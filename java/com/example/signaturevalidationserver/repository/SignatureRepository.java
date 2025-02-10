package com.example.signaturevalidationserver.repository;

import com.example.signaturevalidationserver.model.User;
import com.example.signaturevalidationserver.model.VerifiedFiles;
import com.example.signaturevalidationserver.model.VerifiedSignatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignatureRepository extends JpaRepository<VerifiedSignatures, Long> {
    @Query("SELECT s FROM VerifiedSignatures s WHERE s.file = :file")
    List<VerifiedSignatures> findAllByFile(@Param("file") VerifiedFiles file);
    @Query("SELECT s FROM VerifiedSignatures s")
    List<VerifiedSignatures> findAllVerifiedSignatures();
    @Query("SELECT vs FROM VerifiedSignatures vs JOIN vs.file f WHERE f.user = :user")
    List<VerifiedSignatures> findAllSignaturesByUser(@Param("user") User user);

}

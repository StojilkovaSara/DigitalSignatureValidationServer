package com.example.signaturevalidationserver.service;

import com.example.signaturevalidationserver.model.Validation;
import com.example.signaturevalidationserver.model.VerifiedFiles;
import com.example.signaturevalidationserver.model.VerifiedSignatures;
import com.example.signaturevalidationserver.model.exceptions.NoSignatureException;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.List;

public interface SignatureValidationService {
    public Pair<VerifiedFiles, List<VerifiedSignatures>> validateDigitalSignatures(File file) throws NoSignatureException;
    public List<VerifiedFiles> getAllVerifiedFiles();
    public List<VerifiedSignatures> getAllVerifiedSignatures();
    public List<VerifiedSignatures> getVerifiedSignaturesByFile(VerifiedFiles file);
}

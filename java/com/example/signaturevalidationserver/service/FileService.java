package com.example.signaturevalidationserver.service;


import com.example.signaturevalidationserver.model.VerifiedFiles;

public interface FileService{
    byte[] getFileData(String fileName);

    VerifiedFiles getFileByFileId(Long fileId);
}

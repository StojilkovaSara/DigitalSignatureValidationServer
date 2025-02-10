package com.example.signaturevalidationserver.service.Impl;

import com.example.signaturevalidationserver.model.VerifiedFiles;
import com.example.signaturevalidationserver.repository.FileRepository;
import com.example.signaturevalidationserver.service.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    private FileRepository fileRepository;
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public byte[] getFileData(String file_name) {
        return fileRepository.findByFileName(file_name).getContent();
    }

    @Override
    public VerifiedFiles getFileByFileId(Long fileId) {
        return fileRepository.findByFileId(fileId);
    }

}

package com.example.signaturevalidationserver.service.Impl;

import com.example.signaturevalidationserver.model.*;
import com.example.signaturevalidationserver.model.exceptions.NoSignatureException;
import com.example.signaturevalidationserver.repository.FileRepository;
import com.example.signaturevalidationserver.repository.SignatureRepository;
import com.example.signaturevalidationserver.service.SignatureValidationService;
import com.example.signaturevalidationserver.service.UserService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.SignatureUtil;

import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Security;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SignatureValidationServiceImpl implements SignatureValidationService {
    private final UserService userService;
    private final FileRepository fileRepository;
    private final SignatureRepository signatureRepository;

    public SignatureValidationServiceImpl(UserService userService, FileRepository fileRepository,
                                          SignatureRepository signatureRepository) {
        this.userService = userService;
        this.fileRepository = fileRepository;
        this.signatureRepository = signatureRepository;
    }

    @Override
    public Pair<VerifiedFiles, List<VerifiedSignatures>> validateDigitalSignatures(File file) throws NoSignatureException {
        VerifiedFiles verifiedFile;
        List<Signature> signatures = new ArrayList<Signature>();
        List<VerifiedSignatures> verifiedSignatures= new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] content = Files.readAllBytes(file.toPath());
            verifiedFile = new VerifiedFiles(content, file.getName(), file.getPath(), userService.getCurrentUser());
            // Initialize PDF reader
            PdfReader reader = new PdfReader(fis);
            PdfDocument pdfDoc = new PdfDocument(reader);
            SignatureUtil signUtil = new SignatureUtil(pdfDoc);

            // Retrieve signature names from the PDF
            List<String> signatureNames = signUtil.getSignatureNames();
            if (signatureNames.size() == 0) {
                verifiedFile.setValid(true);
                verifiedFile.setSignatures(null);
                fileRepository.save(verifiedFile);
                throw new NoSignatureException("There are no digital signatures in the attached file");
            }

            boolean validation = true;

            // Loop through signatures and validate them
            for (String name : signatureNames) {
                Security.addProvider(new BouncyCastleProvider());
                PdfPKCS7 pkcs7 = signUtil.readSignatureData(name);

                //Extract information
                String signer = pkcs7.getSignName();
                Calendar signDate = pkcs7.getSignDate();
                int revision = signUtil.getRevision(name);
                int totalRevision = signUtil.getTotalRevisions();

                //Check validity
                boolean integrityAndAuthenticity = pkcs7.verifySignatureIntegrityAndAuthenticity();
                boolean signatureCoversWholeDocument = signUtil.signatureCoversWholeDocument(name);
                boolean certificateValidity = true;
                boolean certificateExpired = false;
                boolean certificateNotYetValid = false;
                try {
                    pkcs7.getSigningCertificate().checkValidity();
                } catch (CertificateExpiredException e) {
                    certificateValidity = false;
                    certificateExpired = true;
                } catch (CertificateNotYetValidException e) {
                    certificateValidity = false;
                    certificateNotYetValid = true;
                }

                if (!integrityAndAuthenticity || !signatureCoversWholeDocument || !certificateValidity) {
                    System.out.println("Signature verification failed for: " + name);
                    validation = false;
                }
                Signature signature = new Signature(name, integrityAndAuthenticity, signatureCoversWholeDocument,
                        certificateValidity, certificateExpired, certificateNotYetValid, signDate, signer, revision, totalRevision);
                VerifiedSignatures verifiedSignature = new VerifiedSignatures(name, integrityAndAuthenticity,
                        signatureCoversWholeDocument, certificateValidity, certificateExpired, certificateNotYetValid,
                        signDate, signer, revision, totalRevision, verifiedFile);
                signatures.add(signature);
                verifiedSignatures.add(verifiedSignature);
            }
            verifiedFile.setValid(validation);
            verifiedFile.setSignatures(verifiedSignatures);

            fileRepository.save(verifiedFile);
            signatureRepository.saveAll(verifiedSignatures);

            Pair<VerifiedFiles, List<VerifiedSignatures>> result = Pair.of(verifiedFile, verifiedSignatures);
            return result;

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<VerifiedFiles> getAllVerifiedFiles(){
        User user = userService.getCurrentUser();
        if(user.getRole().getAuthority().equals("ROLE_ADMIN")){
            return fileRepository.findAllVerifiedFiles();
        }
        else{
            return fileRepository.findAllByUser(user);
        }
    }
    @Override
    public List<VerifiedSignatures> getAllVerifiedSignatures(){
        User user = userService.getCurrentUser();
        if(user.getRole().getAuthority().equals("ROLE_ADMIN")){
            return signatureRepository.findAllVerifiedSignatures();
        }
        else{
            return signatureRepository.findAllSignaturesByUser(user);
        }
    }
    @Override
    public List<VerifiedSignatures> getVerifiedSignaturesByFile(VerifiedFiles file){
        return signatureRepository.findAllByFile(file);
    }
}
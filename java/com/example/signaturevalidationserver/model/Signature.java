package com.example.signaturevalidationserver.model;

import java.util.Calendar;

public class Signature {
    private String signatureName;
    private boolean integrityAndAuthenticity;
    private boolean signatureCoversWholeDocument;
    private boolean certificateValidity;
    private boolean certificateExpired;
    private boolean certificateNotYetValid;

    private Calendar signDate;
    private String signer;
    int revision;
    int totalRevision;

    public Signature(String signatureName, boolean integrityAndAuthenticity, boolean signatureCoversWholeDocument,
                     boolean certificateValidity, boolean certificateExpired, boolean certificateNotYetValid,
                     Calendar signDate, String signer, int revision, int totalRevision) {
        this.signatureName = signatureName;
        this.integrityAndAuthenticity = integrityAndAuthenticity;
        this.signatureCoversWholeDocument = signatureCoversWholeDocument;
        this.certificateValidity = certificateValidity;
        this.certificateExpired = certificateExpired;
        this.certificateNotYetValid = certificateNotYetValid;
        this.signDate = signDate;
        this.signer = signer;
        this.revision = revision;
        this.totalRevision = totalRevision;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public boolean checkIntegrityAndAuthenticity() {
        return integrityAndAuthenticity;
    }

    public Calendar getSignDate() {
        return signDate;
    }

    public String getSigner() {
        return signer;
    }

    public boolean checkSignatureCoversWholeDocument() {
        return signatureCoversWholeDocument;
    }

    public boolean checkCertificateValidity() {
        return certificateValidity;
    }
    public boolean checkCertificateExpired(){
        return certificateExpired;
    }
    public boolean checkCertificateNotYetValid(){
        return certificateNotYetValid;
    }

    public int getRevision() {
        return revision;
    }

    public int getTotalRevision() {
        return totalRevision;
    }
}

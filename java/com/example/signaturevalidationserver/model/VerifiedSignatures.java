package com.example.signaturevalidationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@Entity
@Table(name = "signatures")
public class VerifiedSignatures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long signature_id;
    @Column(name = "signatureName", nullable = false)
    private String signatureName;
    @Column(name = "integrityAndAuthenticity", nullable = false)
    private boolean integrityAndAuthenticity;
    @Column(name = "signatureCoversWholeDocument", nullable = false)
    private boolean signatureCoversWholeDocument;
    @Column(name = "certificateValidity", nullable = false)
    private boolean certificateValidity;
    @Column(name = "certificateExpired", nullable = false)
    private boolean certificateExpired;
    @Column(name = "certificateNotYetValid", nullable = false)
    private boolean certificateNotYetValid;
    @Column(name = "signDate", nullable = false)
    private Calendar signDate;
    @Column(name = "signer", nullable = false)
    private String signer;
    @Column(name = "revision", nullable = false)
    int revision;
    @Column(name = "totalRevision", nullable = false)
    int totalRevision;
    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false, referencedColumnName = "file_id")
    private VerifiedFiles file;
    public VerifiedSignatures(){}

    public VerifiedSignatures(String name, boolean integrityAndAuthenticity, boolean signatureCoversWholeDocument,
                              boolean certificateValidity, boolean certificateExpired, boolean certificateNotYetValid,
                              Calendar signDate, String signer, int revision, int totalRevision, VerifiedFiles file) {
        this.signatureName = name;
        this.integrityAndAuthenticity = integrityAndAuthenticity;
        this.certificateValidity = certificateValidity;
        this.certificateExpired = certificateExpired;
        this.signatureCoversWholeDocument = signatureCoversWholeDocument;
        this.certificateNotYetValid = certificateNotYetValid;
        this.signDate = signDate;
        this.signer = signer;
        this.revision = revision;
        this.totalRevision = totalRevision;
        this.file = file;
    }
}
